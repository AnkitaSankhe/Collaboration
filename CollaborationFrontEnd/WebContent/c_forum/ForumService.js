'use strict';

app.factory('ForumService', ['$http', '$q', '$location',function($http,$q,$location)
{
	console.log("ForumService..")
	
	var BASE_URL='http://localhost:8080/hubback2';
	
	var factory=
	{
			fetchAllForums:fetchAllForums,
			createForum:createForum,
			DeleteForum:DeleteForum
	};
	
	return factory;
	
	
	function fetchAllForums()
	{
		// Create a Deferred object
		var deferred =$q.defer();

		// Call the $http get service
		$http.get(BASE_URL+'/ForumDetails/')
			 .then(function (response) {
			 	deferred.resolve(response.data);
			 },function (errResponse) {
			 	console.error('Error while fetching Forums!');
			 	deferred.reject(errResponse);
		});

		// return the promise object
		return deferred.promise;	 

	}
	
	
	function createForum(user)
		{
			alert('inside Forum Service.jsp');
			console.log('inside forum service with '+user)
			return $http.post(BASE_URL+'/ForumSave/', user)
			.then(
					function(response){
						return response.data;
						
					},
					function(errResponse){
						console.error('Error while creating user');
						return $q.reject(errResponse);
					});
		}
	
	function DeleteForum(user)
	{
		console.log('inside Forum service with '+user)
		return $http.post(BASE_URL+'/admin/del/'+user) 
		.then(
				function(response){
					return response.data;
					
				},
				function(errResponse){
					console.error('Error while creating forum');
					return $q.reject(errResponse);
				});
	}
	

	
	
}])