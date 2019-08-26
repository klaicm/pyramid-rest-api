package klaicm.backlayer.tennisscores.services.map;

import klaicm.backlayer.tennisscores.model.Player;
import klaicm.backlayer.tennisscores.services.PlayerService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PlayerServiceMap extends AbstractMapService<Player, Long> implements PlayerService {

    @Override
    public Set<Player> findAll() {
        return super.findAll();
    }

    @Override
    public Player findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Player save(Player object) {
        return super.save(object);
    }

    @Override
    public void delete(Player object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Player findByLastName(String lastName) {
        return null;
    }
}
