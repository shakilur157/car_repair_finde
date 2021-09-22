package car.repair.finder.controllers;

import car.repair.finder.models.User;
import car.repair.finder.models.UserRoles;
import car.repair.finder.payload.request.LoginRequest;
import car.repair.finder.payload.request.SignupRequest;
import car.repair.finder.payload.response.JwtResponse;
import car.repair.finder.payload.response.MessageResponse;
import car.repair.finder.payload.response.commonResponse.CommonErrorResponse;
import car.repair.finder.payload.response.commonResponse.CommonResponse;
import car.repair.finder.repositories.UserRepository;
//import car.repair.finder.repositories.ServiceCentreRepository;
import car.repair.finder.repositories.UserRolesRepositories;
import car.repair.finder.security.jwt.JwtUtils;
import car.repair.finder.security.services.UserDetailsImpl;
import car.repair.finder.user_type.UserType;
import car.repair.finder.utils.CommonUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRolesRepositories userRolesRepositories;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;


	CommonUtils commonUtils = new CommonUtils();

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getPhone(), loginRequest.getPin()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority).collect(Collectors.toList());

			if(commonUtils.findValidUser(roles, loginRequest.getUserType())){
				return ResponseEntity.ok(
						new JwtResponse(HttpStatus.OK.value(), "success", jwt,
										userDetails.getId(),
										userDetails.getUsername(),
										userDetails.getPhone(),
										roles));
			}else {
				return  new ResponseEntity<CommonErrorResponse>(new CommonErrorResponse(HttpStatus.FORBIDDEN.value(), "Error: id or password is incorrect"), HttpStatus.FORBIDDEN);
			}
		}catch (Exception exception){
			return  new ResponseEntity<CommonErrorResponse>(new CommonErrorResponse(HttpStatus.FORBIDDEN.value(), "Error: id or password is incorrect"), HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (signUpRequest.getPhone() == null) {
			return new ResponseEntity<CommonErrorResponse>(new CommonErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(),"Error: User name is empty"), HttpStatus.NOT_ACCEPTABLE);
		}
		if (userRepository.existsByPhone(signUpRequest.getPhone())) {
			return new ResponseEntity<CommonErrorResponse>(new CommonErrorResponse(HttpStatus.BAD_REQUEST.value(),"Error: User name is already taken"), HttpStatus.BAD_REQUEST);
		}
		if (signUpRequest.getPassword().length() < 4) {
			return new ResponseEntity<CommonErrorResponse>(new CommonErrorResponse(HttpStatus.BAD_REQUEST.value(),"Error: Password should nto be less than 4 char"), HttpStatus.BAD_REQUEST);
		}
		User user;
		try{
			 user = new User(signUpRequest.getName(),
					signUpRequest.getPhone(),
					encoder.encode(signUpRequest.getPassword()));
		}catch (Exception exception){
			return new ResponseEntity<CommonErrorResponse>(new CommonErrorResponse(400, exception.getMessage()), HttpStatus.BAD_REQUEST);
		}

		Set<String> strRoles = signUpRequest.getRole();
		Set<UserRoles> roles = new HashSet<>();

		if (strRoles == null || strRoles.size() == 0) {
			return new ResponseEntity<CommonErrorResponse>(new CommonErrorResponse(400, "Error: User role can not be null"), HttpStatus.BAD_REQUEST);
		} else {
			strRoles.forEach (role -> {
				if ("service_centre".equals(role)) {
					UserRoles serviceCentre = userRolesRepositories.findByUserType(UserType.SERVICE_CENTRE)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(serviceCentre);
				}else if("admin".equals(role)){
					UserRoles adminRole = userRolesRepositories.findByUserType(UserType.ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
				} else if("car_owner".equals(role)) {
					UserRoles userRole = userRolesRepositories.findByUserType(UserType.CAR_OWNER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		if(roles.size() > 0){
			user.setRoles(roles);
			userRepository.save(user);
			return new ResponseEntity<CommonResponse>(new CommonResponse(200, "success", "sign up success"), HttpStatus.OK);
		}else{
			return new ResponseEntity<CommonErrorResponse>(new CommonErrorResponse(400, "Error: Not a valid user type"), HttpStatus.BAD_REQUEST);
		}
	}
}
