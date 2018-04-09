package Network;


import Service.ILoginService;
import Service.IParticipationService;
import Service.ISaleService;
import Utils.Observer;

public interface IClientProxy extends ILoginService, ISaleService, IParticipationService {

    void addObserver(Observer observer);

    void notifyAllObservers();

}
