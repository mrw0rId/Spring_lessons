package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.entity.User;
import ru.geekbrains.persist.UserRepository;
import ru.geekbrains.persist.UserSpecification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserRepr> findAllProductsByUserId(long id) {
        return userRepo.findAllProductsByUserId(id)
                .stream()
                .map(UserRepr::new)
                .collect(Collectors.toList()
                );
    }

    @Override
    public List<UserRepr> findAll() {
        return userRepo.findAll()
                .stream()
                .map(UserRepr::new)
                .collect(Collectors.toList()
                );
    }

    @Transactional
    @Override
    public Optional<UserRepr> findById(Long id) {
        return userRepo.findById(id)
                .map(UserRepr::new);
    }

    @Transactional
    @Override
    public Optional<UserRepr> findById(String id) {
        long idd = Long.parseLong(id);
        return userRepo.findById(idd)
                .map(UserRepr::new);
    }

    @Transactional
    @Override
    public List<UserRepr> findByName(String userName) {
        return userRepo.findByUserName(userName)
                .stream()
                .map(UserRepr::new)
                .collect(Collectors.toList()
                );
    }

    @Override
    public Page<UserRepr> findWithFilter(String userName, Integer minAge, Integer maxAge,
                                         Integer page, Integer size , String sort) {
        Specification<User> spec = Specification.where(null);
        Page<UserRepr> users;

        if (userName != null && !userName.isBlank()) {
            spec = spec.and(UserSpecification.userNameLike(userName));
        }
        if (minAge != null) {
            spec = spec.and(UserSpecification.minAge(minAge));
        }
        if (maxAge != null) {
            spec = spec.and(UserSpecification.maxAge(maxAge));
        }
        if(sort != null && !sort.isBlank()) {
            users = userRepo
                    .findAll(spec, PageRequest.of(page, size, Sort.by(sort)))
                    .map(UserRepr::new);
        }else users = userRepo
                .findAll(spec, PageRequest.of(page, size))
                .map(UserRepr::new);

        return users;
    }

    @Transactional
    public void save(UserRepr user) {
        User userToSave = new User(user);
        userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));
        userRepo.save(userToSave);
        if(user.getId()==null) {
            user.setId(userToSave.getId());
        }
    }

    @Transactional
    @Override
    public void delete(long id) {
        userRepo.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByName(String userName) {
        userRepo.deleteByUserName(userName);
    }

    @Transactional
    @Override
    public void delete(String id) {
        long idd = Long.parseLong(id);
        userRepo.deleteById(idd);
    }
}
