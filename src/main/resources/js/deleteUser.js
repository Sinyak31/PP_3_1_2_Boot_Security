async function deleteUserData(userId){
    await fetch(`/api/admin/${userId}`, {method: 'DELETE'});
}

const modalDelete = document.getElementById("deleteModal");

async function DeleteModalHandler() {
    await fillModal(modalDelete);
}

const formDelete = document.getElementById("modalBodyDelete");
formDelete.addEventListener("submit", async function(event) {
    event.preventDefault();

    const userId = event.target.querySelector("#idDelete").value;
    await deleteUserData(userId);
    await fillTableOfAllUsers();

    const modalBootstrap = bootstrap.Modal.getInstance(modalDelete);
    modalBootstrap.hide();
    }
)