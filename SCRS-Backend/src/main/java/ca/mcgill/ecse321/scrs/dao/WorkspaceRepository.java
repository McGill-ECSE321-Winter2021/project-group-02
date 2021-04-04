package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.Workspace;
import org.springframework.data.repository.CrudRepository;

public interface WorkspaceRepository extends CrudRepository<Workspace, Integer>
{

    Workspace findByWorkspaceID(int id);

    Workspace findBySpaceName(String name);
}
