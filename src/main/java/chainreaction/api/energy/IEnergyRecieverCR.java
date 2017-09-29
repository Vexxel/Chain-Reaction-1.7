package chainreaction.api.energy;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;

public interface IEnergyRecieverCR extends IEnergyReceiver {
    EnergyStorage getEnergyStorage();
}
