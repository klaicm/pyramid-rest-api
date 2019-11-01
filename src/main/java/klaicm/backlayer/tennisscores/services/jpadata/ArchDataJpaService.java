package klaicm.backlayer.tennisscores.services.jpadata;

import klaicm.backlayer.tennisscores.model.ArchData;
import klaicm.backlayer.tennisscores.repositories.ArchDataRepository;
import klaicm.backlayer.tennisscores.services.ArchDataService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ArchDataJpaService implements ArchDataService {

    private final ArchDataRepository archDataRepository;

    public ArchDataJpaService(ArchDataRepository archDataRepository) {
        this.archDataRepository = archDataRepository;
    }

    @Override
    public Set<ArchData> findAll() {
        Set<ArchData> archData = new HashSet<>();
        archDataRepository.findAll().forEach(archData::add);
        return archData;
    }

    @Override
    public ArchData findById(Long aLong) {
        return archDataRepository.findById(aLong).orElse(null);
    }

    @Override
    public ArchData save(ArchData object) {
        return archDataRepository.save(object);
    }

    @Override
    public void delete(ArchData object) {
        archDataRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        archDataRepository.deleteById(id);
    }

    @Override
    public Set<ArchData> getArchDataByPlayerId(Long id) {
        return archDataRepository.getArchDataByPlayerId(id);
    }
}
