<?php

/** This class manipulate Article object in DB. 
*
* @file ArticleDataMapper.php
*/

namespace Mapper;

use Model\Article;

class ArticleDataMapper implements PersistenceInterface, FinderInterface
{
    /** Name of the table */
    private $tableName = "articles";
    
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
     * @return array
     */
    public function findAll($criterias = null) {
        $articles = array();       
        $query = 'SELECT * FROM :tableName';
        
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
        
        foreach($results as $article){
            $articles[] = new Article($article->id, $article->name, $article->descrption);
        }

        return $articles;
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
            $article = $result[0];
        }
        
        return new Article($article->id, $article->name, $article->descrption);
    }

    /** Render an article peristante
    *
    *@param $article Article
    *
    *@return array
    */
    public function persist($article){
        if($article->isNew()){
                return $this->insert($article);
        }

        return $this->update($article);
    }

    /** Delete an article from the database.
    *
    *@param $article Article
    *
    *@return array
    */
    public function remove($article){
        $query = 'DELETE FROM :tableName WHERE id = :id';

        return $this->database->executeQuery($query, array(
                'tableName' => $this->tableName,
				'id' => $article->getId()));
    }

    /** Insert an article in the database.
    *
    *@param $article Article
    *
    *@return
    */
    public function insert($article){
        $query = 'INSERT INTO :tableName (id, name, description) 
                  VALUES (null, :name, :description)';

        return $this->database->executeQuery($query, array(
                'tableName' => $this->tableName,
				'name' => $article->getName(),
                'description' => $article->getDescription()));
    }

    /** Update an article in the database.
    *
    *@param $article Article
    *
    *@return array
    */
    public function update($article){
        $query = 'UPDATE :tableName
                  SET name = :name, description = :description
                  WHERE id = :id';

        return $this->database->executeQuery($query, array(
                'tableName' => $this->tableName,
				'id' => $article->getId(),
                'name' => $article->getName(),
                'descrption' => $article->getDescription()));
    } 
}
