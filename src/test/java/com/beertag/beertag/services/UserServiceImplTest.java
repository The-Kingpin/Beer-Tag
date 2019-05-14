package com.beertag.beertag.services;

import com.beertag.entities.User;
import com.beertag.model.RegistrationModel;
import com.beertag.repository.RoleRepository;
import com.beertag.repository.UserRepository;
import com.beertag.serviceImpl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

        @Mock
        UserRepository userRepository;

        @Mock
        RoleRepository roleRepository;

        @Mock
        BCryptPasswordEncoder bCryptPasswordEncoder;

        @Mock
        ModelMapper modelMapper;

        @Mock
        RegistrationModel registrationModel;

        @InjectMocks
        UserServiceImpl userService;


    @Test
    public void findByUsername_Should_ReturnCorrectUser() {

        Mockito.when(userRepository.findOneByUsername("user1"))
                .thenReturn(new User("FirstName", "LastName", "e-mail","user1", true));

        User result = userService.getUserByUsername("user1");

        Assert.assertEquals("user1", result.getUsername());
    }

    @Test
    public void GetAllUsers_ReturnCorrectSizeOfUsers() {
        Mockito.when(userRepository.findAll())
                .thenReturn(Arrays.asList(
                        new User("FirstName1", "LastName1", "e-mail","user1",true),
                        new User("FirstName2", "LastName2", "e-mail","user2",true),
                        new User("FirstName3", "LastName5", "e-mail","user3", true)
                ));

        List<User> result = userService.getAllUsers();

        Assert.assertEquals(3, result.size());
        Assert.assertTrue(result.get(0).isEnabled());
        Assert.assertTrue(result.get(1).isEnabled());
        Assert.assertTrue(result.get(2).isEnabled());
    }

    @Test
    public void getUserWhenUserIdGiven_ShouldReturnCorrectUser() {
        Mockito.when(userRepository.getUserById(1))
                .thenReturn(new User("FirstName1", "LastName1", "e-mail","user1",true));

        User result = userService.getUserById(1);

        Assert.assertEquals("user1", result.getUsername());
    }

    @Test
    public void loadUserByUsername_ShouldReturnCorrectUser(){
        Mockito.when(userRepository.findOneByUsername("user1"))
                .thenReturn(new User("FirstName1", "LastName1", "e-mail","user1",true));

        UserDetails result = userService.loadUserByUsername("user1");

        Assert.assertEquals("user1", result.getUsername());
    }

    @Test
    public void deleteUser_ShouldReturnDisabledUser(){
        Mockito.when(userRepository.findUserById(1))
                .thenReturn(new User("FirstName1", "LastName1", "e-mail","user1",true));

        User testUser = userRepository.findUserById(1);
        userService.deleteUser(1);

        Assert.assertFalse(testUser.isEnabled());
    }

    @Test
    public void UpdateBeer_ShouldReturnCorrectUpdatedBeer() {

        User user = new User();
        user.setId(1);

        Mockito.when(userRepository.getUserById(1)).thenReturn(user);

        User updUser = new User();
        updUser.setUserPicture(null);
        updUser.setFirstName("fname");
        updUser.setLastName("lname");
        updUser.setPassword(bCryptPasswordEncoder.encode("bla"));
        updUser.setEmail("mail");

        userService.updateUser(updUser, 1);

        Mockito.verify(userRepository, Mockito.times(1)).getUserById(1);
        Assert.assertEquals("fname", updUser.getFirstName());
        Assert.assertEquals("lname", updUser.getLastName());
    }

//    @Test
//    public void RegisterUser_ShouldCreateNewUser() throws UserAlreadyExistException {
//
//        //ModelMapper mp = new ModelMapper();
//        RegistrationModel model = new RegistrationModel();
////        model.setPassword("1111");
////        model.setUsername("name");
//        User user = modelMapper.map(registrationModel, User.class);
////        User existingUser = new User();
////        existingUser.setId(1);
////        existingUser.setUsername("user1");
//        Mockito.when(userRepository.findOneByUsername("user1")).thenReturn(null);
//
//        Set<Role> roles = new HashSet<>();
//        Role role = new Role();
//        role.setId(1);
//        role.setAuthority("ROLE_USER");
//        roles.add(role);
//        Mockito.when(roleRepository.findByAuthority("ROLE_USER")).thenReturn(role);
//
//        String encryptedPassword = bCryptPasswordEncoder.encode(registrationModel.getPassword());
//        user.setId(1);
//        user.setPassword(encryptedPassword);
//        user.setAccountNonExpired(true);
//        user.setAccountNonLocked(true);
//        user.setAuthorities(roles);
//        user.setEnabled(true);
//        user.setCredentialsNonExpired(true);
//
//        userService.register(model);
//
//        Mockito.verify(userRepository, Mockito.times(1)).findOneByUsername("user1");
//    }
}
