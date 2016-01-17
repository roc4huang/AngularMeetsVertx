/**
 * Created by martindobrev on 16/01/16.
 */

(function() {


    var app = angular.module('callManager', []);


    app.controller("MainController", ['$http', function($http) {
        this.number = null;
        this.calls = [];
    }]);

    app.directive('numberInput', function() {
        return {
            restrict: 'E',
            templateUrl: 'directives/number_input.html',
            controller: function() {
                this.number = null;

                this.submitNumber = function(mainController, logController) {
                    mainController.number = this.number;
                    logController.refresh();
                    this.number = null;
                }
            },
            controllerAs: 'inputCtrl'
        }
    });

    app.directive('callLog', ['$http', function($http) {
        return {
            restrict: 'E',
            templateUrl: 'directives/call_log.html',
            controller: function() {

                this.calls = [];

                var t = this;
                this.refresh = function() {

                    $http.get('/calls').success(function(data) {
                        var selectedNumber = $('#your_number').text();
                        var relevantCalls = [];
                        for (var i = 0; i < data.length; i++) {
                            if (data[i].callee === selectedNumber
                                || data[i].caller === selectedNumber) {
                                relevantCalls.push(data[i]);
                            }
                        }

                        t.calls = relevantCalls;
                    });
                };

                this.refresh();
            },
            controllerAs: 'logCtrl'
        };
    }]);


    app.directive('callSimulator', ['$http', '$interval', function($http, $interval) {
        return {
            restrict: 'E',
            templateUrl: 'directives/call_simulator.html',
            require: 'callLog',
            link: function(scope, element, attrs, callLogCtrl) {

            },
            controller: function() {

                this.callee = null;
                this.duration = null;
                this.promise = null;
                this.start = null;

                var t = this;

                this.tick = function() {
                    t.duration = t.duration + 1;
                };

                this.startCall = function() {
                    t.duration = 0;
                    t.start = Date.now();
                    t.promise = $interval(t.tick, 1000);
                };

                this.stopCall = function(logCtrl) {
                    $interval.cancel(t.promise);

                    var object = {
                        callee: t.callee,
                        caller: $('#your_number').text(),
                        duration: t.duration,
                        start: t.start
                    };

                    $http.post('/calls/add', object).success(function(data) {

                        t.timerId = null;
                        t.duration = null;
                        t.callee = null;


                        logCtrl.refresh();

                    });
                }
            },
            controllerAs: 'callSimulatorCtrl'
        }
    }]);


})();
