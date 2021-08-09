package by.kostopravov.polyclinic.sirvice;

import by.kostopravov.polyclinic.dto.Address;
import by.kostopravov.polyclinic.repository.IAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    IAddressRepository addressRepository;

    @Autowired
    public AddressService(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address checkAddress(Address newAddress) {
        Address checkAddress = addressRepository.findByCityAndStreetAndHouseAndFlat(
                newAddress.getCity(), newAddress.getStreet(), newAddress.getHouse(), newAddress.getFlat()).
                orElse(null);
        if (checkAddress == null) {
            addressRepository.save(newAddress);
            checkAddress = newAddress;
        }
        return checkAddress;
    }
}
