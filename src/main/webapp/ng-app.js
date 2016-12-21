(function () {
    var deps = ['angularBootstrapNavTree', 'ngTable', 'ngAnimate'];

    var app = angular.module('EmployeeTimesheetApp', deps);

    app.controller('EmployeeTimesheetController', ['$scope', '$http', 'NgTableParams', function ($scope, $http, NgTableParams) {

        function formatTreeData(response) {
            var result = response;
            if( result.prototype.toString.call( result ) !== '[object Array]' ) {
                result = [response];
            }

            for (var i = 0; i < result.length; i++) {
                var curEl = result[i];
                curEl.label = curEl.lastName + ' ' + curEl.firstName.charAt(0);
                curEl.children = curEl.employees;
                formatTreeData(curEl.children);
            }
            return result;
        }

        $scope.emp_tree_handler = function (branch) {
            var _ref;
            $scope.output = "You selected: " + branch.lastName + ' ' + branch.firstName;
            if ((_ref = branch.data) != null ? _ref.description : void 0) {
                return $scope.output += '(' + branch.data.description + ')';
            }
        };

        $scope.tree_control = {};
        $scope.emp_tree_data =  [];
        $http.get('employees').then(function (response) {
            var serverResponse = response.data;
            $scope.emp_tree_data = formatTreeData(serverResponse);

            var tree = $scope.tree_control;
            tree.expand_all();
        });

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


        /*
         var treedata_avm2 = [
         {
         label: 'Бунин И. А.',
         children: [
         {
         label: 'Ахматова А. А.',
         data: {
         description: "11111"
         }
         }, {
         label: 'Бальмонт К. Д.',
         data: {
         description: "22222"
         }
         }, {
         label: 'Мережковский Д. С.',
         data: {
         description: "33333"
         }
         }, {
         label: 'Адамович Г. В.',
         children: ['Гумилёв Н. С.', 'Брюсов В. Я.', 'Введенский А. И.']
         }
         ]
         }, {
         label: 'Кузмин М. А.',
         data: {
         definition: "444444",
         data_can_contain_anything: true
         },
         children: [
         {
         label: 'Минский Н. М.'
         }, {
         label: 'Меркурьева В. А.',
         children: [
         {
         label: 'Маслов Г. В.'
         }, {
         label: 'Лозинский М. Л.'
         }, {
         label: 'Лившиц Б. К.'
         }
         ]
         }
         ]
         }, {
         label: 'Маяковский В. В.',
         children: [
         {
         label: 'Иванов Г. В.',
         children: ['Грузинов И. В.', 'Анисимов Ю. П.', 'Бородаевский В. В.']
         }, {
         label: 'Блок А. А.',
         children: ['Добролюбов А. М.', 'Городецкий С.М.', 'Большаков К. А.']
         }, {
         label: 'Карпов П. И.',
         children: [
         {
         label: 'Перцов П. П.',
         children: ['Марр Ю. Н.', 'Липскеров К. А.', 'Клюев Н. А.', 'Анненский И. Ф.']
         }, {
         label: 'Потёмкин П. П.',
         children: ['Клычков С. А.', 'Казанская Т. Б.', 'Зенкевич М.А.', 'Гофман В. В.', 'Гофман М. Л.']
         }
         ]
         }
         ]
         }
         ];*/


    }]);

}).call(this);