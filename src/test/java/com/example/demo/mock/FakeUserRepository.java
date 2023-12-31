package com.example.demo.mock;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.service.port.UserRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class FakeUserRepository implements UserRepository {

    /*
    사실 소형 테스트라서 단일 쓰레드에서 돌아가기 때문에 동기화 걱정을 안해줘도 된다.
    AtomicLong, Collections.synchronizedList 는 안써도 됨.
     */
    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<User> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public User getById(final long id) {
        return findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @Override
    public Optional<User> findById(final long id) {
        return data.stream().filter(item -> Objects.equals(item.getId(), id)).findAny();
    }

    @Override
    public Optional<User> findByIdAndStatus(final long id, final UserStatus userStatus) {
        return data.stream().filter(
                        item -> Objects.equals(item.getId(), id)
                                && item.getStatus() == userStatus)
                .findAny();
    }

    @Override
    public Optional<User> findByEmailAndStatus(final String email, final UserStatus userStatus) {
        return data.stream().filter(
                        item -> Objects.equals(item.getEmail(), email)
                                && item.getStatus() == userStatus)
                .findAny();
    }

    @Override
    public User save(final User user) {
        if (user.getId() == null || user.getId() == 0) {
            User newUser = User.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .address(user.getAddress())
                    .certificationCode(user.getCertificationCode())
                    .status(user.getStatus())
                    .lastLoginAt(user.getLastLoginAt())
                    .build();
            data.add(newUser);
            return newUser;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), user.getId()));
            data.add(user);
            return user;
        }
    }
}
