package chainreaction.api.energy;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;

public interface IEnergyHandlerCR extends IEnergyHandler {
    EnergyStorage getEnergyStorage();
}
