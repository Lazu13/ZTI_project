import {
    API_BASE_URL, ACCESS_TOKEN, SERVICE_TOKEN,
    INVITATIONS_LIST_SIZE, MATCHES_LIST_SIZE, LEAGUES_LIST_SIZE, LEAGUEPOINTS_LIST_SIZE
} from '../constants';

const request = (options) => {
    const headers = new Headers();

    const accessToken = localStorage.getItem(ACCESS_TOKEN);

    if (accessToken !== null && accessToken !== undefined) {
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', 'Bearer ' + accessToken)
    } else {
        if (options.optionalContent !== null && options.optionalContent !== undefined) {
            headers.append('Content-Type', options.optionalContent);
        } else {
            headers.append('Content-Type', 'application/json');
        }
        headers.append('Authorization', 'Basic ' + SERVICE_TOKEN)
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options)
        .then(response =>
            response.json().then(json => {
                if (!response.ok) {
                    return Promise.reject(json);
                }
                return json;
            })
        );
};


export function login(loginRequest) {
    localStorage.removeItem(ACCESS_TOKEN);

    const grantType = {"grant_type": "password"};
    const data = Object.assign({}, grantType, loginRequest);

    const searchParams = Object.keys(data).map((key) => {
        return encodeURIComponent(key) + '=' + encodeURIComponent(data[key])
    }).join('&');

    return request({
        url: API_BASE_URL + "/oauth/token",
        method: 'POST',
        body: searchParams,
        optionalContent: 'application/x-www-form-urlencoded'
    });
}

export function signup(signupRequest) {
    localStorage.removeItem(ACCESS_TOKEN);

    return request({
        url: API_BASE_URL + "/users",
        method: 'POST',
        body: JSON.stringify(signupRequest)
    })
}

export function getCurrentUser() {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/users/",
        method: 'GET'
    });
}

export function getUserLike(regexName, page, size) {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/users/like/" + regexName + "?page=" + page + "&size=" + size,
        method: 'GET'
    });
}

export function deleteUser(userId) {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/users/" + userId,
        method: 'DELETE'
    });
}


export function inviteUser(userId, leagueId) {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/leagues/" + leagueId + "/users/" + userId + "/invite",
        method: 'POST'
    });
}

export function getInvitations(page, size) {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    page = page || 0;
    size = size || INVITATIONS_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/leagues/invitations?page=" + page + "&size=" + size,
        method: 'GET'
    });
}

export function acceptInvitation(leagueId) {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/leagues/" + leagueId + "/invitation/accept",
        method: 'POST'
    });
}

export function rejectInvitation(leagueId) {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/leagues/" + leagueId + "/invitation/reject",
        method: 'POST'
    });
}

export function leaveLeague(leagueId) {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/leagues/" + leagueId + "/leave",
        method: 'POST'
    });
}

export function deleteLeague(leagueId) {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/leagues/" + leagueId,
        method: 'DELETE'
    });
}


export function createLeague(leagueRequest) {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/leagues/",
        method: 'POST',
        body: JSON.stringify(leagueRequest)
    });
}

export function getMyLeagues(page, size) {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    page = page || 0;
    size = size || LEAGUES_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/leagues?page=" + page + "&size=" + size,
        method: 'GET'
    });
}

export function getLeaguePoints(leagueId, page, size) {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    page = page || 0;
    size = size || LEAGUEPOINTS_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/points/current/ " + leagueId + "?page=" + page + "&size=" + size,
        method: 'GET'
    });
}

export function getAllLeagues(page, size) {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    page = page || 0;
    size = size || LEAGUES_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/leagues/all?page=" + page + "&size=" + size,
        method: 'GET'
    });
}


export function makePrediction(predictionBody) {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/predictions/make",
        method: 'POST',
        body: JSON.stringify(predictionBody)
    });
}

export function getPredictions(page, size) {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    page = page || 0;
    size = size || MATCHES_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/predictions?page=" + page + "&size=" + size,
        method: 'GET'
    });
}


export function getNextMatch() {
    return request({
        url: API_BASE_URL + "/next_match",
        method: 'GET'
    })
}

export function getDeadline() {
    return request({
        url: API_BASE_URL + "/deadline",
        method: 'GET'
    })
}

export function getGroupMatches(page, size) {
    page = page || 0;
    size = size || MATCHES_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/matches/all/group?page=" + page + "&size=" + size,
        method: 'GET'
    })
}

export function getKnockoutMatches(page, size) {
    page = page || 0;
    size = size || MATCHES_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/matches/all/knockout?page=" + page + "&size=" + size,
        method: 'GET'
    })
}
