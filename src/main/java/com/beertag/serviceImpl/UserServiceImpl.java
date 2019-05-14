package com.beertag.serviceImpl;

import com.beertag.config.Errors;
import com.beertag.entities.Beer;
import com.beertag.entities.Role;
import com.beertag.entities.User;
import com.beertag.model.RegistrationModel;
import com.beertag.repository.BeerRepository;
import com.beertag.repository.RoleRepository;
import com.beertag.repository.UserRepository;
import com.beertag.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           UserRepository userRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void register(RegistrationModel registrationModel) throws Exception {
        User user = this.modelMapper.map(registrationModel, User.class);

        User existingUser = userRepository.findOneByUsername(user.getUsername());

        if (existingUser!= null){
            throw new Exception("Username exists");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByAuthority("ROLE_USER"));

        String encryptedPassword = this.bCryptPasswordEncoder.encode(registrationModel.getPassword());
        user.setPassword(encryptedPassword);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setAuthorities(roles);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);


        this.userRepository.save(user);
    }

    @Override
    public void delete() {
        System.out.println("DELETE TOPIC");
    }

    @Override
    public User getUserByUsername(String user) {
        return userRepository.findOneByUsername(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(isDel -> isDel.isEnabled()==true)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findOneByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(Errors.INVALID_CREDENTIALS);
        }

        return user;
    }

    @Override
    public void deleteUser(int id) {
        User user = userRepository.findUserById(id);
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public User updateUser(User user, int id){
        User existUser = userRepository.getUserById(id);

        existUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        existUser.setEmail(user.getEmail());
        existUser.setFirstName(user.getFirstName());
        existUser.setLastName(user.getLastName());
        existUser.updateUeserPicture(user.getUserPicture());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);

        return userRepository.save(existUser);

    }
}
