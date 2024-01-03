const modalTemp = document.getElementById("modal-temp");
const controllerPanel = document.querySelector('.btn-container');

const span = document.getElementsByClassName("close")[0];

span.onclick = function() {
    removeModalContext();
}

function removeModalContext(){
    modalTemp.style.display = "none";
    const controllerPanel = document.querySelector('.btn-container');
    while (controllerPanel.firstChild) {
        controllerPanel.removeChild(controllerPanel.firstChild);
    }
}

window.onclick = function(event) {
    if (event.target === modalTemp) {
        removeModalContext();
    }
}
// '/model3d/delete-new-confirming?id='
function deleteNewsConfirming(id, url){
    axios.post(url)
        .then(response => {
            if(response.data){
                const elementToDelete = document.getElementById(id);
                if(elementToDelete){
                    elementToDelete.remove();
                    removeModalContext();
                }

                console.log('Delete successful:', response.data);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function openModalRemove(button){
    const id = button.getAttribute('data-id');
    const url = button.getAttribute('data-url');
    const deleteButton = createButton("Delete", "btn-delete");
    deleteButton.onclick = function (){
        deleteNewsConfirming(id, url);
    };
    const cancelButton = createButton("Cancel", "btn-cancel");
    cancelButton.onclick = function () {
        removeModalContext();
        modalTemp.style.display = 'none';
    }


    controllerPanel.appendChild(deleteButton);
    controllerPanel.appendChild(cancelButton);
    modalTemp.style.display = 'block';
}


function createButton(name, classes,){
    const action = document.createElement("button");
    action.textContent = name;
    action.className = classes

    return action;
}


function updateDescriptionColor(selectElement) {
    const cameraId = selectElement.getAttribute('data-camera-id');
    const cameraPointIdValue = document.getElementById("cameraPointId" + cameraId).value;
    const selectedColorValue = selectElement.value;
    const url = "/camera-point/update-camera-point-description-color?id=" + cameraPointIdValue + "&selectedColor=" + selectedColorValue;

    axios.get(url)
        .then(response => {
            console.log(response);
        })
        .catch(err => {
            console.log(err);
        });
}
