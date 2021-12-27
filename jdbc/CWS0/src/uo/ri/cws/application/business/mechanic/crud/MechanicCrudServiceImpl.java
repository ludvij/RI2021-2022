package uo.ri.cws.application.business.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicCrudService;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.mechanic.crud.commands.AddMechanic;
import uo.ri.cws.application.business.mechanic.crud.commands.DeleteMechanic;
import uo.ri.cws.application.business.mechanic.crud.commands.FindAllMechanics;
import uo.ri.cws.application.business.mechanic.crud.commands.FindMechanicByDni;
import uo.ri.cws.application.business.mechanic.crud.commands.FindMechanicById;
import uo.ri.cws.application.business.mechanic.crud.commands.UpdateMechanic;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class MechanicCrudServiceImpl implements MechanicCrudService {
	
	private CommandExecutor ce = new CommandExecutor();
	
	
	@Override
	public MechanicDto addMechanic(MechanicDto mechanic) throws BusinessException {
		if (mechanic == null)
			throw new IllegalArgumentException("invalid mechanic");
		return ce.execute(new AddMechanic(mechanic));
	}

	@Override
	public void deleteMechanic(String idMechanic) throws BusinessException {
		if (idMechanic == null || idMechanic.isBlank())
			throw new IllegalArgumentException("Invalid id");
		ce.execute(new DeleteMechanic(idMechanic));	
	}

	@Override
	public void updateMechanic(MechanicDto mechanic) throws BusinessException {
		if (mechanic == null)
			throw new IllegalArgumentException("invalid mechanic");
		ce.execute(new UpdateMechanic(mechanic));	
		
	}

	@Override
	public Optional<MechanicDto> findMechanicById(String idMechanic) throws BusinessException {
		return ce.execute(new FindMechanicById(idMechanic));
	}

	@Override
	public Optional<MechanicDto> findMechanicByDni(String dniMechanic) throws BusinessException {
		return ce.execute(new FindMechanicByDni(dniMechanic));
	}

	@Override
	public List<MechanicDto> findAllMechanics() throws BusinessException {
		return ce.execute(new FindAllMechanics());
	}

}
