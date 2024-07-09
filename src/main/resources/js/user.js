document.addEventListener('DOMContentLoaded', async function () {
    await showUserEmailOnNavbar()
    await fillTableAboutUser();
});

async function dataAboutCurrentUser() {
    const response = await fetch("/api/user")
    return await response.json();
}
async function fillTableAboutUser(){
    const currentUserTable1 = document.getElementById("currentUserTable");
    const currentUser = await dataAboutCurrentUser();

    let currentUserTableHTML = "";
    currentUserTableHTML +=
        `<tr>
            <td>${currentUser.id}</td>
            <td>${currentUser.username}</td>
            <td>${currentUser.surname}</td>
            <td>${currentUser.age}</td>
            <td>${currentUser.email}</td>
            <td>${currentUser.roleList.map(role => role.role).join(' ')}</td>
        </tr>`
    currentUserTable1.innerHTML = currentUserTableHTML;
}

async function showUserEmailOnNavbar() {
    const currentUserEmailNavbar = document.getElementById("currentUserEmailNavbar")
    const currentUser = await dataAboutCurrentUser();
    currentUserEmailNavbar.innerHTML =
        `<strong>${currentUser.email}</strong>
                 with roles: 
                 ${currentUser.roleList.map(role => role.role).join(' ')}`;
}

