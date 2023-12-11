function bar(ctx,label,dataset,title,type){
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: label,
            datasets: [{
                label: title,
                data: dataset,
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

function multiBar(ctx,label,datasets,titles,color){
    var datasete = new Array();
    var i = 0;
    for(i = 0 ; i < datasets.length ; i++){
        datasete.push({label:titles[i].title,data : datasets[i],backgroundColor:color[i].color});
    }
    alert(datasete.toString())
    new Chart(ctx,{
            type:'bar',
            data : {
            labels: label,
            datasets: [datasete]
            },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
        });
}