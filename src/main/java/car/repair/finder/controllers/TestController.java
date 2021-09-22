package car.repair.finder.controllers;

import car.repair.finder.models.*;
import car.repair.finder.payload.request.AcceptServiceRequest;
import car.repair.finder.payload.request.Prices;
import car.repair.finder.payload.response.commonResponse.CommonResponse;
import car.repair.finder.payload.response.commonResponse.UserInformationResponse;
import car.repair.finder.repositories.*;
import car.repair.finder.security.jwt.JwtUtils;
import car.repair.finder.security.services.UserDetailsServiceImpl;
import car.repair.finder.status.Status;
import car.repair.finder.user_type.UserType;
import car.repair.finder.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	UserInformationRepository userInformationRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	ServicesRepository servicesRepository;

	@Autowired
	ServiceRequestRepository serviceRequestRepository;

	@Autowired
	RequestedServiceRepository requestedServiceRepository;

	@Autowired
	PriceChartRepository priceChartRepository;

	@Autowired
	ServiceProviderRequestRepository serviceProviderRequestRepository;



	CommonUtils commonUtils = new CommonUtils();

	@PostMapping("/user/information")
	@PreAuthorize("hasAuthority('CAR_OWNER') or hasAuthority('SERVICE_CENTRE')")
	public String userAccess(@RequestHeader Map<String, String> authHeader,
											 @RequestBody UserInformation requestBody) {

		try {
			UserType userType = commonUtils.getUserRoleFromUserType(requestBody.getUserType().toUpperCase());
			String jwtFromHeader = commonUtils.getJWTFromHeader(authHeader);
			Long useId = jwtUtils.getUserIdFromJwtToken(jwtFromHeader);
			if (useId != null){
				UserInformation information = new UserInformation(
						useId,
						requestBody.getFullName(),
						requestBody.getAddress(),
						requestBody.getSex(),
						userType.toString()
						);
				userInformationRepository.save(information);
				return "successfully inserted";
			}else {
				return "id not found";
			}
		}catch (Exception exception){
			return "address must not be null";
		}

	}

	@GetMapping("/user/information/{userType}")
	@PreAuthorize("hasAuthority('CAR_OWNER') or hasAuthority('SERVICE_CENTRE')")
	public ResponseEntity<?> userInformation(@RequestHeader Map<String, String> authHeader, @PathVariable String userType) {
		try {
			UserType uType = commonUtils.getUserRoleFromUserType(userType.toUpperCase());
			String jwtFromHeader = commonUtils.getJWTFromHeader(authHeader);
			Long useId = jwtUtils.getUserIdFromJwtToken(jwtFromHeader);
			if (useId != null){
				Optional<List<UserInformation>> information = userInformationRepository.findAllByUserIdAndUserType(useId, uType.toString());
				if(information.isPresent()){
					List<UserInformationResponse> response = new ArrayList<UserInformationResponse>();
					for (int i=0;i<information.get().size(); i++){
						response.add(new UserInformationResponse(information.get().get(i).getUserInfoId(),
								information.get().get(i).getAddress(),
								information.get().get(i).getFullName(),
								information.get().get(i).getSex()));
					}
					return new ResponseEntity<CommonResponse>(new CommonResponse(200, "success", response), HttpStatus.OK);
				}else {
					return new ResponseEntity<String>("no records found", HttpStatus.OK);
				}
			}else {
				return new ResponseEntity<String>("bad request", HttpStatus.BAD_REQUEST);
			}
		}catch (Exception ex){
			return new ResponseEntity<String>("bad request", HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/user/service_add")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> serviceAdd(@RequestBody Services services) {
		try {
			boolean isExist = servicesRepository.existsByName(services.getName());
			if(isExist){
				return new ResponseEntity<String>("already exist", HttpStatus.FORBIDDEN);
			}else {
				servicesRepository.save(services);
				return new ResponseEntity<String>("saved", HttpStatus.OK);
			}

		}catch (Exception exception){
			return new ResponseEntity<String>("could not store data", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/user/all_services")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SERVICE_CENTRE') or hasAuthority('CAR_OWNER')")
	public ResponseEntity<?>getAllServices(){
		try{
			Iterable<Services> services = servicesRepository.findAll();
			return new ResponseEntity<CommonResponse<Iterable<Services>>>(new CommonResponse<>(200, "success", services), HttpStatus.OK);
		}catch (Exception exception){
			return new ResponseEntity<String>("bad request", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/user/service_request")
	@PreAuthorize("hasAuthority('CAR_OWNER')")
	public ResponseEntity<?> requestForAService(@RequestHeader Map<String, String> authHeader,@RequestBody ServiceRequest serviceRequest){
		try {
			String jwtFromHeader = commonUtils.getJWTFromHeader(authHeader);
			Long carOwnerId = jwtUtils.getUserIdFromJwtToken(jwtFromHeader);
			ServiceRequest serviceRequest1 = new ServiceRequest(
					carOwnerId,
					serviceRequest.getServicesId(),
					serviceRequest.getAdditionalMessage(),
					serviceRequest.getLocation()
					);
			serviceRequestRepository.save(serviceRequest1);
			return new ResponseEntity<String>("service request submitted", HttpStatus.OK);
		}catch (Exception exception){
			return new ResponseEntity<String>("could not submit service request", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/user/service_prices")
	@PreAuthorize("hasAuthority('SERVICE_CENTRE')")
	public ResponseEntity<?> setServicePrices(@RequestHeader Map<String, String> authHeader,@RequestBody Prices priceList){
		try {
			String jwtFromHeader = commonUtils.getJWTFromHeader(authHeader);
			Long serviceCentreId = jwtUtils.getUserIdFromJwtToken(jwtFromHeader);
			List<PriceChart> priceChart = new ArrayList<>();
			for(int i=0; i < priceList.getPriceList().size(); i++){
				PriceChart p = new PriceChart(
						priceList.getPriceList().get(i).getPrice(),
						priceList.getPriceList().get(i).getServiceId(),
						serviceCentreId
						);
				priceChart.add(p);
			}
			priceChartRepository.saveAll(priceChart);
			return new ResponseEntity<String>("price added", HttpStatus.OK);
		}catch (Exception exception){
			return new ResponseEntity<String>("could not submit service prices", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/user/service_provider_request")
	@PreAuthorize("hasAuthority('SERVICE_CENTRE')")
	public ResponseEntity<?> submitServiceProviderRequest(@RequestHeader Map<String, String> authHeader,@RequestBody ServiceProviderRequest serviceProviderRequest){
		try {
			String jwtFromHeader = commonUtils.getJWTFromHeader(authHeader);
			Long serviceCentreId = jwtUtils.getUserIdFromJwtToken(jwtFromHeader);

			boolean isExist = serviceProviderRequestRepository.existsByServiceCentreIdAndServiceRequestId(serviceCentreId, serviceProviderRequest.getServiceRequestId());
			if(!isExist){
				ServiceProviderRequest serviceProviderRequest1 = new ServiceProviderRequest(
						serviceProviderRequest.getServiceRequestId(),
						serviceCentreId,
						serviceProviderRequest.getDiscount(),
						serviceProviderRequest.getRepairTime()
				);
				serviceProviderRequestRepository.save(serviceProviderRequest1);
				return new ResponseEntity<String>("request submitted successfully", HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("already submitted request", HttpStatus.BAD_REQUEST);
			}
		}catch (Exception exception){
			return new ResponseEntity<String>("could not submit request", HttpStatus.BAD_REQUEST);
		}
	}
	@PutMapping("/user/accept_request")
	@PreAuthorize("hasAuthority('CAR_OWNER')")
	public ResponseEntity<?> acceptServiceRequest(@RequestHeader Map<String, String> authHeader,@RequestBody AcceptServiceRequest acceptServiceRequest){
		try {
			String jwtFromHeader = commonUtils.getJWTFromHeader(authHeader);
			Long carOwnerId = jwtUtils.getUserIdFromJwtToken(jwtFromHeader);

			Optional<ServiceRequest> serviceRequest = serviceRequestRepository.findById(acceptServiceRequest.getServiceRequestId());
			if (serviceRequest.isPresent()){
				ServiceRequest serviceRequest1 = new ServiceRequest(
						serviceRequest.get().getId(),
						serviceRequest.get().getCarOwnerID(),
						serviceRequest.get().getServicesId(),
						serviceRequest.get().getAdditionalMessage(),
						serviceRequest.get().getLocation(),
						"ACCEPTED",
						acceptServiceRequest.getServiceCentreId(),
						serviceRequest.get().getCreatedAt()
						);
				serviceRequestRepository.save(serviceRequest1);
				return new ResponseEntity<String>("request accepted", HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("could not accept request", HttpStatus.BAD_REQUEST);
			}
		}catch (Exception exception){
			return new ResponseEntity<String>("could not accept request", HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasAuthority('SERVICE_CENTRE')")
	@GetMapping("/user/all_pending_request")
	public ResponseEntity<?> getAllPendingServiceRequests(@RequestHeader Map<String, String> authHeader){
		try {
			String jwtFromHeader = commonUtils.getJWTFromHeader(authHeader);
			Long carOwnerId = jwtUtils.getUserIdFromJwtToken(jwtFromHeader);
			Iterable<ServiceRequest> serviceRequests = serviceRequestRepository.findAll();
			List<ServiceRequest> serviceRequestList = new ArrayList<>();
			for(ServiceRequest req : serviceRequests){
				if (Status.valueOf(req.getStatus()).equals(Status.PENDING)){
					serviceRequestList.add(req);
				}
			}
			return new ResponseEntity<CommonResponse<List<ServiceRequest>>>(new CommonResponse(200, "success", serviceRequestList), HttpStatus.OK);
		}catch (Exception exception){
			return new ResponseEntity<String>("could not retrieve pending requests", HttpStatus.BAD_REQUEST);
		}
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public void handleMissingParams(MissingServletRequestParameterException ex) {
		String name = ex.getParameterName();
		System.out.println(name + " parameter is missing");
	}

}
