package me.mandaveiga.concretesolutions.service;

import me.mandaveiga.concretesolutions.model.AbstractModel;
import me.mandaveiga.concretesolutions.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public abstract class BaseService<T extends AbstractModel> implements CrudService<T> {

    protected BaseRepository<T> repository;

    @Autowired
    public BaseService(BaseRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<T> save(T body) {
        T entity = repository.save(body);

        return Optional.of(entity);
    }

    @Override
    public List<T> findAll() {
        return (List<T>) repository.findAll();
    }

    @Override
    public Optional<T> findById(UUID id) {
        return repository.findById(id);
    }

	@Override
    public Optional<T> update(T body) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, Object> objectMap = objectMapper.convertValue(body, Map.class);
//        objectMap.values().removeIf(Objects::isNull);
//
//        Update update = new Update();
//        objectMap.forEach(update::set);
//
//        mongoTemplate.findAndModify(
//                Query.query(Criteria.where("id").is(body.getId())), update, body.getClass());
//
//        return Optional.of(body);
    	return save(body);
    }

    @Override
    public Optional<Boolean> deleteById(UUID id) {
        try {
            repository.deleteById(id);

            return Optional.of(true);
        } catch(Exception ignore) {
            return Optional.of(false);
        }
    }

    @Override
    public Optional<Boolean> deleteAll() {
        try {
            repository.deleteAll();

            return Optional.of(true);
        } catch(Exception ignore) {
            return Optional.of(false);
        }
    }
}
