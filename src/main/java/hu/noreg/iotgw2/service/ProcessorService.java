package hu.noreg.iotgw2.service;

import hu.noreg.iotgw2.domain.Processor;
import hu.noreg.iotgw2.repository.ProcessorRepository;
import hu.noreg.iotgw2.service.dto.ProcessorDTO;
import hu.noreg.iotgw2.service.mapper.ProcessorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Processor}.
 */
@Service
@Transactional
public class ProcessorService {

    private final Logger log = LoggerFactory.getLogger(ProcessorService.class);

    private final ProcessorRepository processorRepository;

    private final ProcessorMapper processorMapper;

    public ProcessorService(ProcessorRepository processorRepository, ProcessorMapper processorMapper) {
        this.processorRepository = processorRepository;
        this.processorMapper = processorMapper;
    }

    /**
     * Save a processor.
     *
     * @param processorDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcessorDTO save(ProcessorDTO processorDTO) {
        log.debug("Request to save Processor : {}", processorDTO);
        Processor processor = processorMapper.toEntity(processorDTO);
        processor = processorRepository.save(processor);
        return processorMapper.toDto(processor);
    }

    /**
     * Partially update a processor.
     *
     * @param processorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProcessorDTO> partialUpdate(ProcessorDTO processorDTO) {
        log.debug("Request to partially update Processor : {}", processorDTO);

        return processorRepository
            .findById(processorDTO.getId())
            .map(
                existingProcessor -> {
                    processorMapper.partialUpdate(existingProcessor, processorDTO);
                    return existingProcessor;
                }
            )
            .map(processorRepository::save)
            .map(processorMapper::toDto);
    }

    /**
     * Get all the processors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Processors");
        return processorRepository.findAll(pageable).map(processorMapper::toDto);
    }

    /**
     * Get one processor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessorDTO> findOne(Long id) {
        log.debug("Request to get Processor : {}", id);
        return processorRepository.findById(id).map(processorMapper::toDto);
    }

    /**
     * Delete the processor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Processor : {}", id);
        processorRepository.deleteById(id);
    }
}
