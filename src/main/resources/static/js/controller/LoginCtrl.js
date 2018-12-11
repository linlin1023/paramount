
               	app.controller('LoginCtrl', ['$scope','T',
                    function($scope,T) {



                        $scope.login = function(obj) {
                                       		$(obj).attr("disabled", true);

                                       		var username = $.trim($('#username').val());
                                       		var password = $.trim($('#password').val());
                                       		if (username == "" || password == "") {
                                       			$("#info").html(T.T(100005));
                                       			$(obj).attr("disabled", false);
                                       		} else {
                                       			$.ajax({
                                       				type : 'post',
                                       				url : '/sys/login',
                                       				data : $("#login-form").serialize(),
                                       				success : function() {
                                       					location.href = '/';
                                       				},
                                       				error : function(xhr, textStatus, errorThrown) {
                                       					var msg = xhr.responseText;
                                       					var response = JSON.parse(msg);
                                       					$("#info").html(response.message);
                                       					$(obj).attr("disabled", false);
                                       				}
                                       			});

                                       		}
                                       	}

                    }
                ]);