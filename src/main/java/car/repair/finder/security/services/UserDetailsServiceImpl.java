package car.repair.finder.security.services;

import car.repair.finder.models.User;
import car.repair.finder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userRepository;

//	@Autowired
//	ServiceCentreRepository serviceCentreRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
		User user = userRepository.findByPhone(phone)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + phone));

		return UserDetailsImpl.build(user);
	}
}
