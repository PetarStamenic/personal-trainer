package work.stamenic.userservice.seeder

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import work.stamenic.userservice.model.Client
import work.stamenic.userservice.repository.ClientRepository
@Component
class UserSeeder (var clientRepository: ClientRepository): CommandLineRunner{
    override fun run(vararg args: String?) {
        var client = Client(
            membershipCardNumber = "card1",1,
            user = "pera",
            pass = "\$2a\$12\$LEajHsUJyFisGyUlZx7y0OX4Ue9uB99I/Uz9SxORXkyU7MAMcHPLa",
            email = "pera",
            dateOfBirth = System.currentTimeMillis(),
            firstName = "petar",
            lastName = "petar")
        clientRepository.save(client)
    }

}