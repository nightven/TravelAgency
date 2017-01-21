document.addEventListener('DOMContentLoaded', function(){
    var d = new Date();
    var day = d.getDate();
    var month = d.getMonth() + 1;
    var year = d.getFullYear();
    var departure_date = document.getElementById('departure-date')
    var arrival_date = document.getElementById('arrival-date')
    departure_date.min = year + "-" + month + "-" + day;
    arrival_date.min = year + "-" + month + "-" + day;

    departure_date.addEventListener('change', function () {
        arrival_date.min = departure_date.value;
    })


});