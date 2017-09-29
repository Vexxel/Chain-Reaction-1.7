package chainreaction.api.energy;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;

public interface IEnergyProviderCR extends IEnergyProvider {
    EnergyStorage getEnergyStorage();
}
