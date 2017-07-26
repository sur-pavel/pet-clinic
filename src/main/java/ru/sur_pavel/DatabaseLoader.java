package ru.sur_pavel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.sur_pavel.domain.Client;
import ru.sur_pavel.domain.ClientRepository;
import ru.sur_pavel.domain.Pet;
import ru.sur_pavel.domain.PetRepository;

import java.util.Arrays;
import java.util.List;

import static ru.sur_pavel.domain.Pet.Status.NEW;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private static final String[] petNicks = {
            "Ace","Apollo","Bailey","Bandit","Baxter","Bear","Beau","Benji","Benny","Bentley","Blue","Bo","Boomer","Brady","Brody","Bruno","Brutus","Bubba","Buddy","Buster","Cash","Champ","Chance","Charlie","Chase","Chester","Chico","Coco","Cody","Cooper","Copper","Dexter","Diesel","Duke","Elvis","Finn","Frankie","George","Gizmo","Gunner","Gus","Hank","Harley","Henry","Hunter","Jack","Jackson","Jake","Jasper","Jax","Joey","Kobe","Leo","Loki","Louie","Lucky","Luke","Mac","Marley","Max","Mickey","Milo","Moose","Murphy","Oliver","Ollie","Oreo","Oscar"
    };

    private static final List<Client> clients = Arrays.asList(
            new Client("John", "Trench"),
            new Client("Bob", "Marley"),
            new Client("Sam", "Brown")
    );


    private final ClientRepository clientRepository;

    private final PetRepository petRepository;

    @Autowired
    public DatabaseLoader(ClientRepository clientRepository, PetRepository petRepository) {
        this.clientRepository = clientRepository;
        this.petRepository = petRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        clientRepository.save(clients);
        for (int i = 0; i < 9; i++) {
            petRepository.save(new Pet(petNicks[i], clients.get(0), NEW));
        }
        for (int i = 10; i < 19; i++) {
            petRepository.save(new Pet(petNicks[i], clients.get(1), NEW));
        }
        for (int i = 20; i < petNicks.length; i++) {
            petRepository.save(new Pet(petNicks[i], clients.get(2), NEW));
        }

    }
}
