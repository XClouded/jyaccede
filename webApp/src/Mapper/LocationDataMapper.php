<?php 

namespace Mapper;

use Model\Location;

class LocationDataMapper implements PersistenceInterface, FinderInterface
{
	private $tableName = "location";
	
	private $database;
	
	public function __construct(\Dal\Database $database){
		$this->database = $database;
		
	}
	
	public function findAll($criterias = null) {
		$location = array();
		$query = 'SELECT * FROM :location'
		
		if($criterias !== null){
			if(!empty($criterias['where'])){
				$query .= ' WHERE '.$criterias['where']
			}
			
			if(!empty($criterias['order'])){
				$query .= ' ORDER BY '.$criterias['order'];
			}
			
			if(!empty($criterias['limit'])){
				$query .= ' LIMIT 0,'.$criterias['limit'];
			}
		}
		
		$results = $this->database->executeQuery($query, array('tableName' => $this->tableName));
		
		foreach($results as $location){
			$location[] = new Location($location->id, $location->name, $location->latitude, $location->longitude, $location->mark, $location->idCategory, $location->disabledAccess);			
		}
		
		return $location;
	}
	
	public function findOneById($id) {
		$query = 'SELECT * FROM :location WHERE id = :id';
		
		$result = $this->database->executeQuery($query, array('tableName' => $this->tableName, 'id' => $id));
		
		if($result == null){
			return null;
		}
		else{
			$location = $result[0];
		}
		
		return new Location($location->id, $location->name, $location->latitude, $location->longitude, $location->mark, $location->idCategory, $location->disabledAccess);
	}
	
	public function persist($location){
		if($location->isNew()){
			return $this->insert($location);
		}
		return $this->update($location);
	}
	
	public function remove($location){
		$query = 'DELETE FROM :location WHERE id = :id';
		
		return $this->database->executeQuery($query, array(
				'tableName' => $this->tableName,
				'id' => $article->getId()));			
	}
	
	public function insert($location){
		$query = 'INSERT INTO :location (id, name, latitude, longitude, mark, idCategory, disabledAccess)
				  VALUES (null, :name, :latitude, :longitude, :mark, :idCategory, :disabledAccess)';
				  				
		return $this->database->executeQuery($query, array(
				'tableName' => $this->tableName,
				'name' => $location->getName(),
				'latitude' => $location->getLatitude(),
				'longitude' => $location->getLongitude(),
				'mark' => $location->getMark(),
				'idCategory' => $location->getIdCategory(),
				'disabledAccess' => $location->getDisabledAccess()));
	}
	
	public function update($location){
		$query = 'UPDATE :location
				  SET name = :name, latitude = :latitude, longitude = :longitude, mark = :mark, idCategory = :idCategory, disabledAccess = :disabledAccess
				  WHERE id = :id';
		
		return $this->database->executeQuery($query, array(
				'tableName' => $this->tableName,
				'id' => $location->getId(),
				'name' => $location->getName(),
				'latitude' => $location->getLatitude(),
				'longitude' => $location->getLongitude(),
				'mark' => $location->getMark(),
				'idCategory' => $location->getIdCategory(),
				'disabledAccess' => $location->getDisabledAccess()));				
	}
}
?>