package Service;

import Domain.Sale;
import Domain.Show;

import java.util.List;

public interface ISaleService {

      void addVanzare(String clientName,int desiredSeats,int showCode,int codAg) throws  Exception;

      List<Show> getSpec();

      List<Sale> getAllSales();
}
