
//stores camera data
var cam;
var isRotating = true;

const HEIGHT_MULTIPLIER = 3;
const DISTANCE_MULTIPLIER = 400;

//if mouse is pressed in canvas, freeze/unfreeze view
function mousePressed() {
    if (mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height) {
        isRotating = !isRotating;
    }
}

//allows for panning around the model while keeping focussed on the origin
function cameraMovement() {
    //angles in radians
    let x = PI * (2 * (mouseX / width) - 1);
    let y = (mouseY / height) * 2 - 1;
    let pos = createVector(sin(x), y * HEIGHT_MULTIPLIER, cos(x)).normalize().mult(DISTANCE_MULTIPLIER);
    cam.setPosition(pos.x, pos.y, pos.z);
    //look at world origin
    cam.lookAt(0,0,0);
}