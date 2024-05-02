package com.api.petradar.valuation;

import com.api.petradar.user.User;
import com.api.petradar.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ValuationService {

    @Autowired
    private ValuationRepository valuationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Valuation> getValuationsPlace(String placeId) {
        List<Valuation> placeValuations = new ArrayList<>();

        try {
            List<Valuation> allPlaceValuations = valuationRepository.findByPlaceId(placeId);

            if (!allPlaceValuations.isEmpty()) {
                for (Valuation valuation : allPlaceValuations) {
                    if (!valuation.isReported()) {
                        User user = userRepository.findUserById(valuation.getUserId());
                        valuation.setUserName(user.getUsername());
                        placeValuations.add(valuation);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("getValuesPlace: " + e);

        }
        return placeValuations;
    }

    public boolean createValuation(Valuation valuation) {
        boolean alreadyValuated = valuationRepository.alreadyValuated(valuation.getUserId(), valuation.getPlaceId());
        boolean isCreated = false;

        if (!alreadyValuated) {
            valuation.setTimeCreated(new Date(System.currentTimeMillis()));
            valuation.setReported(false);
            valuationRepository.save(valuation);
            isCreated = true;
        } else {
            System.out.println("Ya has valorado este lugar");
        }

        return isCreated;

    }

    public boolean updateValuation(Valuation valuation) {
        boolean isUpdated = false;

        if (valuation != null) {
            valuationRepository.updateById(valuation.getId(), valuation);
            isUpdated = true;
        }

        return isUpdated;

    }

    public boolean deleteValuation(String id) {
        boolean exists = valuationRepository.existsById(id);
        boolean isDeleted = false;

        if (exists) {
            valuationRepository.deleteById(id);
            isDeleted = true;
        }

        return isDeleted;
    }

}
