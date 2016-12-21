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

            var fromDate = $scope.fromDate;
            var fromParam = fromDate.getYear() + "-" + (fromDate.getMonth() + 1) + "-" + fromDate.getDay();
            var toDate = $scope.toDate;
            var toParam = toDate.getYear() + "-" + (toDate.getMonth() + 1) + "-" + toDate.getDay();

            $http.get('worksheets/' + fromParam + '/' + toParam + '/').then(function (response) {
                var serverResponse = response.data;
                setGridData(getDataSet(serverResponse), getColumns($scope.fromDate, $scope.toDate));
            });
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
        var serverResponse = [{
            "employeeId": 1,
            "name": "Ivanov Ivan",
            "workTimes": [{
                "id": 1,
                "firstEntry": {
                    "hour": 8,
                    "minute": 0,
                    "second": 0,
                    "nano": 0
                },
                "lastExit": {
                    "hour": 16,
                    "minute": 0,
                    "second": 0,
                    "nano": 0
                },
                "totalOfficeTime": {
                    "hour": 8,
                    "minute": 0,
                    "second": 0,
                    "nano": 0
                },
                "pureOfficeTime": {
                    "hour": 7,
                    "minute": 0,
                    "second": 0,
                    "nano": 0
                },
                "date": {
                    "id": 1,
                    "year": 2016,
                    "month": 12,
                    "day": 16
                }
            }]
        }];

        function formatNum(num) {
            return (num > 9 ? '' : '0') + num;
        }

        function getDataSet(response) {
            var dataSet = [];
            for (var i = 0; i < response.length; i++) {
                var employee = response[i];
                var employeeData = {id: employee.employeeId, name: employee.name};
                for (var j = 0; j < employee.workTimes.length; j++) {
                    var wt = employee.workTimes[j];
                    var dateValue = formatNum(wt.pureOfficeTime.hour) + ":" + formatNum(wt.pureOfficeTime.minute);
                    var dateKey = formatNum(wt.date.month) + formatNum(wt.date.day);
                    employeeData[dateKey] = dateValue;
                }
                dataSet.push(employeeData);
            }
            console.log("DataSet was built");
            return dataSet;
        }

        function getColumns(fromDate, toDate) {
            var idField = {field: "id", title: "Id", show: "false", sortable: "id"};
            var nameField = {field: "name", title: "Name", show: "true", sortable: "name", filter: {name: "text"}};
            var cols = [idField, nameField];
            var diffDays = Math.ceil((toDate - fromDate) / (1000 * 3600 * 24));
            console.log("DiffDays: " + diffDays);
            for (var i = 0; i <= diffDays; i++) {
                var colDate = new Date();
                colDate.setDate(fromDate.getDate() + i);
                var month = formatNum(colDate.getMonth() + 1);
                var day = formatNum(colDate.getDate());
                var colDateKey = month + day;
                var colDateValue = month + "." + day;

                var dateCol = {field: colDateKey, title: colDateValue, show: "true", sortable: "true"};
                cols.push(dateCol);
            }
            return cols;
        }

        function setGridData(dataSet, cols) {
            $scope.tableParams = new NgTableParams({
                page: 1, // show first page
                count: 10 // count per page
            }, {
                filterDelay: 0,
                dataset: dataSet
            });
            $scope.cols = cols;
        }

        setGridData(getDataSet(serverResponse), getColumns($scope.fromDate, $scope.toDate));

    }]);

}).call(this);