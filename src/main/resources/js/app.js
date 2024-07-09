document.addEventListener('DOMContentLoaded', async function () {
    await showUserEmailOnNavbar()
    await fillTableOfAllUsers();
    await fillTableAboutCurrentUser();
    await addNewUserForm();
    await DeleteModalHandler();
    await EditModalHandler();
});

const ROLE_USER = {id: 1, role: "ROLE_USER"};
const ROLE_ADMIN = {id: 2, role: "ROLE_ADMIN"};
//емаил пользователя в навбаре

async function showUserEmailOnNavbar() {
    const currentUserEmailNavbar = document.getElementById("currentUserEmailNavbar")
    const currentUser = await dataAboutCurrentUser();
    currentUserEmailNavbar.innerHTML =
                `<strong>${currentUser.email}</strong>
                 with roles: 
                 ${currentUser.roleList.map(role => role.role).join(' ')}`;
}
