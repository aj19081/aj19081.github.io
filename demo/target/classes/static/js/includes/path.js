//current path drawing lines
var linePoints = [];
//array of all room positions on 3D model, ordered by room index
var roomPositions = [];

//sets linePoints variable from newLine indices
function setPath(newLine) {
    linePoints = [];
    for (let i = 0; i < newLine.length; i++) {
        linePoints.push(roomPositions[newLine[i]]);
    }
}

//sets roomPositions variable from roomPositions.json
function loadPositions(posJson) {
    roomPositions = new Array(posJson.positions.length);
    for (let i = 0; i < roomPositions.length; i++) {
        roomPositions[i] = posJson.positions[i];
    }

    //set button to a function to get path from back end
    $(function () {
        $("#btn").click(function () {
            $.post({
                    url:"/a1",
                    data:{'name':$("#txtName").val()},
                    success:function(data) {
                        $("#content").html(data);
                        setPath(data)

                    }
                }
            );
        })
    })


}

//draws the line, called every frame
function drawPath() {
    push();
    //positions need to be flipped on z-axis for some reason?
    scale(1, 1, -1);
    stroke(255,0,0);
    strokeWeight(10);
    for (let i = 0; i < linePoints.length - 1; i++) {
        let v1 = linePoints[i];
        let v2 = linePoints[i + 1];
        line(v1[0], v1[1], v1[2], v2[0], v2[1], v2[2]);
    }
    pop();
}