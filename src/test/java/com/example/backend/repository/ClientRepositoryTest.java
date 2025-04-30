package com.example.backend.repository;

import com.example.backend.models.Users;
import com.example.backend.repositories.UsersRepository;
import com.example.backend.utils.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.mockito.Mockito.when;

@DataJpaTest

 class ClientRepositoryTest {

   @Mock
   private UsersRepository usersRepository;

   @Test
   void Users_Repository_test() {
      // Arrange
      Users users = Users.builder()
              .dni(36998745L)
              .email("felipetest@gmail.com")
              .phone("365888945")
              .role(Role.CLIENTS)
              .username("Felipe")
              .build();

      //Act
       users.setId(1L);  // Asignamos un ID al usuario simulado
       when(usersRepository.save(users)).thenReturn(users);

       // Act
       Users saveUsers = usersRepository.save(users);
      //Assert
      Assertions.assertThat(saveUsers.getId()).isEqualTo(users.getId());
      Assertions.assertThat(users.getDni()).isEqualTo(users.getDni());
      Assertions.assertThat(saveUsers.getEmail()).isEqualTo(users.getEmail());
      Assertions.assertThat(users.getPhone()).isEqualTo(users.getPhone());
      Assertions.assertThat(users.getRole()).isEqualTo(users.getRole());
      Assertions.assertThat(saveUsers.getUsername()).isEqualTo(users.getUsername());

   }

   @Test
   void testFindByUsername() {
      // Arrange
      String username = "Felipe";
      Users mockUser = Users.builder()
              .dni(36998745L)
              .email("felipetest@gmail.com")
              .phone("365888945")
              .role(Role.CLIENTS)
              .username(username)
              .build();

      // Simulamos el comportamiento del repositorio
      when(usersRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

      // Act
      Optional<Users> foundUser = usersRepository.findByUsername(username);

      // Assert
      Assertions.assertThat(foundUser).isPresent();
      Assertions.assertThat(foundUser.get().getUsername()).isEqualTo(username);
      Assertions.assertThat(foundUser.get().getEmail()).isEqualTo(mockUser.getEmail());

   }
}
