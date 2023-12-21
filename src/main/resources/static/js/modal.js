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

function deleteNewsConfirming(id){
    axios.post('/model3d/delete-new-confirming?id='+ id)
        .then(response => {
            if(response.data){
                const elementToDelete = document.getElementById(id);
                if(elementToDelete){
                    elementToDelete.remove();
                    removeModalContext();
                }

                console.log('Delete successful:', response.data);
            }
            else{

            }

        })
        .catch(error => {
            // Handle any errors that occur during the request
            console.error('Error:', error);
        });
}

function openModalRemove(button){
    const id = button.getAttribute('data-news-id');
    const deleteButton = createButton("Delete", "btn-delete");
    deleteButton.onclick = function (){
        deleteNewsConfirming(id);
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