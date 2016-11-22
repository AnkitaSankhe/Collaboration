'use strict';
 
angular.module('myApp').controller('ForumController', ['$scope', 'ForumService', '$location', function($scope, ForumService,$location) 
{
	console.log("ForumController.js....")
	
	var self = this;
    self.user={id:'',category:'',title:'',description:''};
    self.forums=[];
 
    fetchAllForums()
    
    function fetchAllForums() 
       {
		ForumService.fetchAllForums()
	    .then(function(data)
	    {
	   		self.forums= data;
		 },function (errResponse) 
		 {
			console.error('Error while fetching the Forums');
		})
	}

 
    
    function createForum(user)
    {
    	alert("inside createForum of ForumController")
    	  ForumService.createForum(user)
          .then(
          
          	self.fetchAllForums,
          
          function(errResponse){
              console.error('Error while creating Forum');
          })
    }
    /*
 
    self.createForum = function (user){
        ForumService.createForum(user)
            .then(
            
            	self.fetchAllForums,
            
            function(errResponse){
                console.error('Error while creating Forum');
            }
        );
    };
 
    self.updateForum = function (user, id){
        ForumService.updateForum(user, id)
            .then(
            self.fetchAllForums,
            function(errResponse){
                console.error('Error while updating Forum');
            }
        );
    };
 */
    
    function deleteForum2(user)
    {
    	console.log("inside DeleteForum2 with forumid= ", user)
    	  ForumService.DeleteForum(user)
          .then(
          
          	self.fetchAllForums,
          
          function(errResponse){
              console.error('Error while deleting Forum');
          })
    }
    
    
    self.deleteForum = function (user)
    {
    	

        	  console.log('Deleting forum with forumid= ', user);
        	  deleteForum2(user);

    };
  
 
    self.submit = function ()
    {
    	alert("Inside Forum Controller");
        if(self.user.id===null){
            console.log('Saving New Forum', self.user);
            createForum(self.user);
        }else{
//            updateUser(self.user, self.user.id);
//            console.log('User updated with id ', self.user.id);
        	  console.log('Saving new Forum 2', self.user);
        	  createForum(self.user);
        	  $location.path('/test');
 
        }
        self.reset();
    };
 
    self.reset = function ()
    {
    	self.user={id:'',category:'',title:'',description:''};
        $scope.myForm.$setPristine(); //reset Form
    }
 
}]);