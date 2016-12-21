(function () {
    var deps = ['angularBootstrapNavTree', 'ngTable', 'ngAnimate', 'ngMaterial'];

    var app = angular.module('EmployeeTimesheetApp', deps);

    app.config(function($mdDateLocaleProvider) {
        // Week starts from Monday.
        $mdDateLocaleProvider.firstDayOfWeek = 1;
    });

    app.controller('EmployeeTimesheetController', ['$scope', '$http', 'NgTableParams', function ($scope, $http, NgTableParams) {

        ////////////////
        //tree config
        $scope.tree_control = {};
        $scope.emp_tree_data =  [];

        function formatTreeData(response) {
            var result = response;
            if( Object.prototype.toString.call( result ) !== '[object Array]' ) {
                result = [response];
            }
            for (var i = 0; i < result.length; i++) {
                var curEl = result[i];
                curEl.label = curEl.name;
                curEl.children = curEl.employees;
                formatTreeData(curEl.children);
            }
            return result;
        }

        $scope.emp_tree_handler = function (branch) {
            var _ref;
            $scope.output = "You selected: " + branch.name;
            if ((_ref = branch.data) != null ? _ref.description : void 0) {
                return $scope.output += '(' + branch.data.description + ')';
            }
        };

        $http.get('employees').then(function (response) {
            var serverResponse = response.data;
            $scope.emp_tree_data = formatTreeData(serverResponse);

            var tree = $scope.tree_control;
        });

        ////////////////
        //Date pickers config

        function getPreviousMonday() {
            var d = new Date();
            var day = d.getDay();
            var diff = d.getDate() - 7 - day + (day == 0 ? -6 : 1); // adjust when day is sunday
            return new Date(d.setDate(diff));
        }

        function getPreviousSunday() {
            var d = new Date();
            var day = d.getDay();
            var diff = d.getDate() - 1 - day + (day == 0 ? -6 : 1); // adjust when day is sunday
            return new Date(d.setDate(diff));
        }

        $scope.fromDate = getPreviousMonday();
        $scope.toDate = getPreviousSunday();

        ////////////////
        //Table config

        var dataSet = [
            {id: 1, name: 'Moroni', age: 50},
            {id: 2, name: 'Drakula', age: 1000},
            {id: 3, name: 'Jun', age: 20}
        ];
        this.tableParams = new NgTableParams({
            page: 1, // show first page
            count: 10 // count per page
        }, {
            filterDelay: 0,
            dataset: dataSet
        });

        this.cols = [
            {field: "name", title: "Name", show: true, sortable: "name", filter: {name: "text"}},
            {field: "age", title: "Age", show: true, sortable: "age"}
        ];

    }]);

}).call(this);