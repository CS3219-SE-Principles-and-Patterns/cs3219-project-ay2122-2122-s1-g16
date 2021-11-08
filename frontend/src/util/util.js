export function isValidEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}

export function parseHtml(raw) {
    raw = raw.replace("\\\"", "")
    raw = raw.replace("<br/><br/>", "<br/>")
    var divOpen = "<div style={{overflow: \"scroll\", height: \"200px\"}}>"
    var divClose = "</div>"
    raw = divOpen + raw + divClose
    return raw
}

export function parseDateTime (raw) {
    var dateTime = new Date(raw)
    return dateTime.toUTCString()
}

export function parseDifficultyLevel (raw) {
    var level = ""
    switch (raw) {
        case "0":
        case 0:
            level = "EASY"
        break;
        case "1":
        case 1:
            level = "MEDIUM"
        break;
        case "2":
        case 2:
            level = "HARD"
        break;
        default:
        break;
    }
    return level
}

export function parseRole (roleCode) {
    if (roleCode === "1") {
        return "interviewer"
    } else {
        return "interviewee"
    }
}

export function isJson(str) {
    try {
        JSON.parse(str);
    } catch (e) {
        return false;
    }
    return true;
}