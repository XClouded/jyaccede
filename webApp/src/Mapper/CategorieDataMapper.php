<?php

/** This class manipulate Categorie object in DB. 
*
* @file CategorieDataMapper.php
*/
namespace Mapper;

use Model\Categorie;

class CategorieDataMapper implements PersistenceInterface, FinderInterface
{
    /** Name of the table */
    private $tableName = "categorie";
    
    /** DataBase access */
    private $database;

    /** Contruct
    * 
    * @param $database Database Data Access Layer
    */ 
    public function __construct(\Dal\DataBase $database){
        $this->database = $database;
    }
    
    /** Returns all elements.
     *
     * @return array
     */
    public function findAll($criterias = null) {
        $articles = array();       
        $query = 'SELECT * FROM :categorie';
        
        if($criterias !== null){
             if(!empty($criterias['where'])){
                $query .= ' WHERE '.$criterias['where'];
            }
            
            if(!empty($criterias['order'])){
                $query .= ' ORDER BY '.$criterias['order'];
            }
            
            if(!empty($criterias['limit'])){
                $query .= ' LIMIT 0,'.$criterias['limit'];
            }
        }
        
        $results = $this->database->executeQuery($query, array('tableName' => $this->tableName));
        
        foreach($results as $categorie){
            $categorie[] = new Categorie($categorie->id, $categorie->name);
        }

        return $categorie;
    }

    /** Retrieve an element by its id.
     *
     * @param  mixed $id
     * @return null|mixed
     */
    public function findOneById($id) {
        $query = 'SELECT * FROM :tableName WHERE id = :id';
        
        $result = $this->database->executeQuery($query, array('tableName' => $this->tableName, 'id' => $id));
        
        if($result === null){
            return null;
        }
        else{
            $categorie = $result[0];
        }
        
        return new Categorie($categorie->id, $categorie->name);
    }

    /** Render an article peristante
    *
    *@param $article Article
    *
    *@return array
    */
    public function persist($categorie){
        if($categorie->isNew()){
            return $this->insert($categorie);
        }

        return $this->update($categorie);
    }

    /** Delete an article from the database.
    *
    *@param $article Article
    *
    *@return array
    */
    public function remove($categorie){
        $query = 'DELETE FROM :tableName WHERE id = :id';

        return $this->database->executeQuery($query, array(
                'tableName' => $this->tableName,
                'id' => $categorie->getId()));
    }

    /** Insert an article in the database.
    *
    *@param $article Article
    *
    *@return
    */
    public function insert($categorie){
        $query = 'INSERT INTO :tableName (id, name) 
                  VALUES (null, :name)';

        return $this->database->executeQuery($query, array(
                'tableName' => $this->tableName,
                'name' => $categorie->getName()));
    }

    /** Update an article in the database.
    *
    *@param $article Article
    *
    *@return array
    */
    public function update($categorie){
        $query = 'UPDATE :tableName
                  SET name = :name
                  WHERE id = :id';

        return $this->database->executeQuery($query, array(
                'tableName' => $this->tableName,
                'id' => $categorie->getId(),
                'name' => $categorie->getName()));
    }
}
