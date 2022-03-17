package example.service.impl;

import example.dao.ClientDao;
import example.entity.Client;
import example.service.ClientService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ClientServiceImpl implements ClientService {
    @Resource
    private ClientDao registeredClientDao;
    @Override
    public Client get(String id) {
        return registeredClientDao.get(id);
    }

    @Override
    public Client getByClientId(String clientId) {
        return registeredClientDao.get(clientId);
    }
}
