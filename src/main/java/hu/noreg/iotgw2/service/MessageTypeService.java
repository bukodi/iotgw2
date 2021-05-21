package hu.noreg.iotgw2.service;

import hu.noreg.iotgw2.domain.MessageType;
import hu.noreg.iotgw2.repository.MessageTypeRepository;
import hu.noreg.iotgw2.service.dto.MessageTypeDTO;
import hu.noreg.iotgw2.service.mapper.MessageTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MessageType}.
 */
@Service
@Transactional
public class MessageTypeService {

    private final Logger log = LoggerFactory.getLogger(MessageTypeService.class);

    private final MessageTypeRepository messageTypeRepository;

    private final MessageTypeMapper messageTypeMapper;

    public MessageTypeService(MessageTypeRepository messageTypeRepository, MessageTypeMapper messageTypeMapper) {
        this.messageTypeRepository = messageTypeRepository;
        this.messageTypeMapper = messageTypeMapper;
    }

    /**
     * Save a messageType.
     *
     * @param messageTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public MessageTypeDTO save(MessageTypeDTO messageTypeDTO) {
        log.debug("Request to save MessageType : {}", messageTypeDTO);
        MessageType messageType = messageTypeMapper.toEntity(messageTypeDTO);
        messageType = messageTypeRepository.save(messageType);
        return messageTypeMapper.toDto(messageType);
    }

    /**
     * Partially update a messageType.
     *
     * @param messageTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MessageTypeDTO> partialUpdate(MessageTypeDTO messageTypeDTO) {
        log.debug("Request to partially update MessageType : {}", messageTypeDTO);

        return messageTypeRepository
            .findById(messageTypeDTO.getId())
            .map(
                existingMessageType -> {
                    messageTypeMapper.partialUpdate(existingMessageType, messageTypeDTO);
                    return existingMessageType;
                }
            )
            .map(messageTypeRepository::save)
            .map(messageTypeMapper::toDto);
    }

    /**
     * Get all the messageTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MessageTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MessageTypes");
        return messageTypeRepository.findAll(pageable).map(messageTypeMapper::toDto);
    }

    /**
     * Get one messageType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MessageTypeDTO> findOne(Long id) {
        log.debug("Request to get MessageType : {}", id);
        return messageTypeRepository.findById(id).map(messageTypeMapper::toDto);
    }

    /**
     * Delete the messageType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MessageType : {}", id);
        messageTypeRepository.deleteById(id);
    }
}
