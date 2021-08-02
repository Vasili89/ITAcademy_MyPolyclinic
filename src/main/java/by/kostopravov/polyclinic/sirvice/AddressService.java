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

    public Address checkAddress(Address address) {
        Address checkAddress = addressRepository.findByCityAndStreetAndHouseAndFlat(
                address.getCity(), address.getStreet(), address.getHouse(), address.getFlat()).
                orElse(null);
        if (checkAddress == null) {
            addressRepository.save(address);
        }
        return addressRepository.findByCityAndStreetAndHouseAndFlat(
                address.getCity(), address.getStreet(), address.getHouse(), address.getFlat()).
                orElse(null);
    }
}
