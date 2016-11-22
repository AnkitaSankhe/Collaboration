//var app = angular.module('myApp', [ 'ngRoute','ngCookies','ngFileUpload' ]);
var app = angular.module('myApp', [ 'ngRoute','ngCookies']);

app.config(function($routeProvider) {
	$routeProvider
	console.log("AppJS...")

	.when("/", {
        templateUrl : 'c_home/home.html'
    })
    .when("/reg", {
        templateUrl : "c_user/register.html",
        controller : "UserController"
    })
   
    .when("/Forum", {
        templateUrl : "c_forum/Forum.html",
        controller : "ForumController",
        controllerAs : "myCtrl"
    })
    
    .when("/ForumView", {
        templateUrl : "c_forum/ForumView.html",
        controller : "ForumController"
    })
    
   .when("/Chat", {
        templateUrl : "ChatView.html",
        controller : "ChatController",
        
    })
    
    .when("/Blog", {
        templateUrl : "c_blog/BlogAdd.html",
        controller : "BlogController",
        controllerAs : "myCtrl"
    })
    
    .when("/BlogView", {
        templateUrl : "c_blog/BlogView.html",
        controller : "BlogController",
        
    })
    
    
    .when("/tvalidate/", {
        templateUrl : "c_user/login.html",
        controller : "LoginController"
    })
/*	.when('/', {
		templateUrl : 'c_home/home.html'
		
	})

	.when('/manageUser', {
		templateUrl : 'c_admin/manage_users.html',
		controller : 'AdminController'
	})

	.when('/event', {
		templateUrl : 'c_upload/upload.html',
		controller : 'FileUploadController'
	})

	.when('/about', {
		templateUrl : 'c_about/about.html',
		controller : 'AboutController'
	})

	.when('/login', {
		templateUrl : 'c_user/login.html',
		controller : 'UserController'
	})

	.when('/register', {
		templateUrl : 'c_user/register.html',
		controller : 'UserController'
	})
	
	.when('/myProfile', {
		templateUrl : 'c_user/my_profile.html',
		controller : 'UserController'
	})

	*//**
	 * Blog related mapping
	 *//*

	.when('/create_blog', {
		templateUrl : 'c_blog/create_blog.html',
		controller : 'BlogController'
	})

	.when('/list_blog', {
		templateUrl : 'c_blog/list_blog.html',
		controller : 'BlogController'
	})

	.when('/view_blog', {
		templateUrl : 'c_blog/view_blog.html',
		controller : 'BlogController'
	})

	*//**
	 * Friend related mapping
	 *//*

	.when('/add_friend', {
		templateUrl : 'c_friend/add_friend.html',
		controller : 'FriendController'
	})

	.when('/search_friend', {
		templateUrl : 'c_friend/search_friend.html',
		controller : 'FriendController'
	})

	.when('/view_friend', {
		templateUrl : 'c_friend/view_friend.html',
		controller : 'FriendController'
	})
	
	.when('/chat', {
		templateUrl : 'c_chat/chat.html',
		controller : 'ChatController'
	})

	*//**
	 * Chat Forum related mapping
	 *//*

	.when('/create_chat_forum', {
		templateUrl : 'c_chat_forum/create_chat_forum.html',
		controller : 'ChatForumController'
	})

	.when('/list_chat_forum', {
		templateUrl : 'c_chat_forum/list_chat_forum.html',
		controller : 'ChatForumController'
	})

	.when('/view_chat_forum', {
		templateUrl : 'c_chat_forum/view_chat_forum.html',
		controller : 'ChatForumController'
	})

	*//**
	 * Job related mappings
	 *//*
	.when('/job', {
		templateUrl : 'c_job/job.html',
		controller : 'JobController'
	})

	.when('/search_job', {
		templateUrl : 'c_job/search_job.html',
		controller : 'JobController'
	})

	.when('/view_applied_jobs', {
		templateUrl : 'c_job/view_applied_jobs.html',
		controller : 'JobController'
	})
	
	.when('/view_job_details', {
		templateUrl : 'c_job/view_job_details.html',
		controller : 'JobController'
	})

	.when('/post_job', {
		templateUrl : 'c_job/post_job.html',
		controller : 'JobController'
	})*/

	.otherwise({
		redirectTo : '/'
	});
});

app.run( function ($rootScope, $location,$cookieStore, $http) {

	 $rootScope.$on('$locationChangeStart', function (event, next, current) {
		 console.log("$locationChangeStart")
		 //http://localhost:8080/Collaboration/addjob
	        // redirect to login page if not logged in and trying to access a restricted page
	        var restrictedPage = $.inArray($location.path(), ['//','/home.html','/','/log', '/reg','/BlogView']) === -1;
	        
	        console.log("restrictedPage:" +restrictedPage)
	        var loggedIn = $rootScope.currentUser.id;
	        
	        console.log("loggedIn:" +loggedIn)
	        
	        if(!loggedIn)
	        	{
	        	
	        	 if (restrictedPage) {
		        	  console.log("Navigating to login page:")
		        	

						            $location.path('/login');
		                }
	        	}
	        
			 else //logged in
	        	{
	        	
				 var role = $rootScope.currentUser.role;
				 var userRestrictedPage = $.inArray($location.path(), ["/post_job"]) == 0;
				 
				 if(userRestrictedPage && role!='admin' )
					 {
					 
					  alert("You can not do this operation as you are logged as : " + role )
					 
					 }
				     
	        	
	        	}
	        
	 }
	       );
	 
	 
	 // keep user logged in after page refresh
     $rootScope.currentUser = $cookieStore.get('currentUser') || {};
     if ($rootScope.currentUser) {
         $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.currentUser; 
     }

});


 
    
    
