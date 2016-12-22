(function () {
    var deps = ['angularBootstrapNavTree', 'ngTable', 'ngAnimate', 'ngMaterial', 'ngCookies'];

    var app = angular.module('EmployeeTimesheetApp', deps);

    app.config(function($mdDateLocaleProvider) {
        // Week starts from Monday.
        $mdDateLocaleProvider.firstDayOfWeek = 1;
    });

    app.controller('EmployeeTimesheetController', ['$scope', '$http', '$cookies', 'NgTableParams', function ($scope, $http, $cookies, NgTableParams) {
        $scope.tree_control = {};
        $scope.emp_tree_data =  [];

        ////////////////
        // authorization
        var uid = $cookies.get('uid');
        if (uid < 1) {
            window.location = "login.html";
        }
        $scope.userId = uid;

        if (uid < 1) {
            return;
        }
        ////////////////
        //tree config

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
            $scope.selectedId = branch.id;
            retrieveWorktimes();
        };


        $http.get('employees/' + uid).then(function (response) {
            var serverResponse = response.data;
            $scope.emp_tree_data = formatTreeData(serverResponse);

            var tree = $scope.tree_control;
        });

        ////////////////
        //Date pickers config

        $scope.changeDateHandler = function () {
            retrieveWorktimes();
        };

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
        function retrieveWorktimes() {
            var fromDate = $scope.fromDate;
            var fromParam = (fromDate.getYear() + 1900) + "-" + formatNum((fromDate.getMonth() + 1)) + "-" + formatNum(fromDate.getDate());
            var toDate = $scope.toDate;
            var toParam = (toDate.getYear() + 1900) + "-" + formatNum((toDate.getMonth() + 1)) + "-" + formatNum(toDate.getDate());

            $http.get('employees/worktimes/' + $scope.selectedId + '?from=' + fromParam + '&to=' + toParam).then(function (response) {
                var serverResponse = response.data;
                setGridData(getDataSet(serverResponse), getColumns($scope.fromDate, $scope.toDate));
            });
        }

        function getDataSet(response) {
            var dataSet = [];
            for (var i = 0; i < response.length; i++) {
                var employee = response[i];
                var employeeData = {id: employee.employeeId, name: employee.employeeName};
                for (var j = 0; j < employee.workTimes.length; j++) {
                    var wt = employee.workTimes[j];
                    var pureOfficeTime = wt.pureOfficeTime;

                    var dateValue =(pureOfficeTime != null)
                        ? formatNum(pureOfficeTime.hour) + ":" + formatNum(pureOfficeTime.minute)
                        : "00:00";

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
                var colDate = new Date(fromDate);
                colDate.setDate(fromDate.getDate() + i);
                var month = formatNum(colDate.getMonth() + 1);
                var day = formatNum(colDate.getDate());
                var colDateKey = month + day;
                var colDateValue = month + "." + day;

                var dateCol = {field: colDateKey, title: colDateValue, show: "true", sortable: colDateKey};
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

        function formatNum(num) {
            return (num > 9 ? '' : '0') + num;
        }

    }]);

}).call(this);