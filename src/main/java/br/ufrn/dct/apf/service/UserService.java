package br.ufrn.dct.apf.service;

import java.util.List;
import java.util.stream.Collectors;

import br.ufrn.dct.apf.dto.UserDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufrn.dct.apf.model.Role;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.repository.RoleRepository;
import br.ufrn.dct.apf.repository.UserRepository;

@Service
public class UserService extends AbstractService {

    private static final int ACTIVE = 1;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(@Qualifier("userRepository") UserRepository userRepository, @Qualifier("roleRepository") RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDTO findUserByEmail(String email) {
        return userRepository.findByEmail(email).convertToDTO();
    }

    /**
     * @param user A new user with default role (USER_ROLE).
     */
    public void save(UserDTO user) {
        Role userRole = roleRepository.findByRoleName(Role.USER_ROLE);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(ACTIVE);

        user.setNewRole(userRole);

        User entity = userRepository.save(user.convertToEntity());

        user.setId(entity.getId());
    }

    public void edit(UserDTO user) {
        UserDTO oldUser = findOne(user.getId());

        if (user.getPassword() == null || user.getPassword().equals("")) {
            user.setPassword(oldUser.getPassword());
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        user.setActive(ACTIVE);
        user.setRoles(oldUser.getRoles());

        userRepository.saveAndFlush(user.convertToEntity());
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream().map(User::convertToDTO).collect(Collectors.toList());
    }

    public UserDTO findOne(Long id) {
        // @TODO Necessita fazer o uso do Optional para garantir a estabilidade
        // Dessa forma pode retornar null
        return userRepository.findById(id).map(User::convertToDTO).orElse(null);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
