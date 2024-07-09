
async function dataAboutAllUsers() {
    const response = await fetch("/api/admin");
    return await response.json();
}

async function dataAboutCurrentUser() {
    const response = await fetch("/api/user")
    return await response.json();
}


//заполнение таблицы всех пользователей в Admin Panel
async function fillTableOfAllUsers() {
    const usersTable = document.getElementById("usersTable");
    const users = await dataAboutAllUsers();


    let usersTableHTML = "";
    for (let user of users) {
        usersTableHTML +=
            `<tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.surname}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${user.roleList.map(role => role.role).join(' ')}</td>
                <td>
                    <button class="btn btn-info btn-sm text-white"
                            data-bs-toggle="modal"
                            data-bs-target="#editModal"
                            data-user-id="${user.id}">
                        Edit</button>
                </td>
                <td>
                    <button class="btn btn-danger btn-sm btn-delete"
                            data-bs-toggle="modal"
                            data-bs-target="#deleteModal"
                            data-user-id="${user.id}">                     
                        Delete</button>
                </td>
            </tr>`;
    }
    usersTable.innerHTML = usersTableHTML;
}


//заполнение таблицы текущего пользователя
async function fillTableAboutCurrentUser(){
    const currentUserTable = document.getElementById("currentUserTable");
    const currentUser = await dataAboutCurrentUser();

    let currentUserTableHTML = "";
    currentUserTableHTML +=
        `<tr>
            <td>${currentUser.id}</td>
            <td>${currentUser.username}</td>
            <td>${currentUser.surname}</td>
            <td>${currentUser.age}</td>
            <td>${currentUser.password}</td>
            <td>${currentUser.roleList.map(role => role.role).join(' ')}</td>
        </tr>`
    currentUserTable.innerHTML = currentUserTableHTML;
}