package hu.noreg.iotgw2.service;

import hu.noreg.iotgw2.domain.KeyPair;
import hu.noreg.iotgw2.repository.KeyPairRepository;
import hu.noreg.iotgw2.service.dto.KeyPairDTO;
import hu.noreg.iotgw2.service.mapper.KeyPairMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KeyPair}.
 */
@Service
@Transactional
public class KeyPairService {

    private final Logger log = LoggerFactory.getLogger(KeyPairService.class);

    private final KeyPairRepository keyPairRepository;

    private final KeyPairMapper keyPairMapper;

    public KeyPairService(KeyPairRepository keyPairRepository, KeyPairMapper keyPairMapper) {
        this.keyPairRepository = keyPairRepository;
        this.keyPairMapper = keyPairMapper;
    }

    /**
     * Save a keyPair.
     *
     * @param keyPairDTO the entity to save.
     * @return the persisted entity.
     */
    public KeyPairDTO save(KeyPairDTO keyPairDTO) {
        log.debug("Request to save KeyPair : {}", keyPairDTO);
        KeyPair keyPair = keyPairMapper.toEntity(keyPairDTO);
        keyPair = keyPairRepository.save(keyPair);
        return keyPairMapper.toDto(keyPair);
    }

    /**
     * Partially update a keyPair.
     *
     * @param keyPairDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<KeyPairDTO> partialUpdate(KeyPairDTO keyPairDTO) {
        log.debug("Request to partially update KeyPair : {}", keyPairDTO);

        return keyPairRepository
            .findById(keyPairDTO.getId())
            .map(
                existingKeyPair -> {
                    keyPairMapper.partialUpdate(existingKeyPair, keyPairDTO);
                    return existingKeyPair;
                }
            )
            .map(keyPairRepository::save)
            .map(keyPairMapper::toDto);
    }

    /**
     * Get all the keyPairs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<KeyPairDTO> findAll(Pageable pageable) {
        log.debug("Request to get all KeyPairs");
        return keyPairRepository.findAll(pageable).map(keyPairMapper::toDto);
    }

    /**
     * Get one keyPair by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<KeyPairDTO> findOne(Long id) {
        log.debug("Request to get KeyPair : {}", id);
        return keyPairRepository.findById(id).map(keyPairMapper::toDto);
    }

    /**
     * Delete the keyPair by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete KeyPair : {}", id);
        keyPairRepository.deleteById(id);
    }
}
