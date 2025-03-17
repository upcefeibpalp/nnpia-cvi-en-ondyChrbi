let jedi1 = "Anakin Skywalker";
const jedi2 = "Obi-Wan Kenobi";

jedi1 = "Darth Vader";

console.log(`Jedi1: ${jedi1}`);
console.log(`Jedi2: ${jedi2}`);

const numberOfSiths = "1";
const numberOfJedis = 1;

if (numberOfJedis === numberOfSiths) {
    console.log("Sily jsou vyrovnane");
}

const jedisAlternative = Array.of("Yoda", jedi1, jedi2);
const jedis = ["Yoda", jedi1, jedi2];

jedis.filter(j => !j.includes("Darth"))
    .map(j => `Jedi: ${j}`)
    .forEach(j => console.log(j));

if (jedis.includes(jedi1)) {
    console.log("There is an impostor among us");
}

const [jedi4, jedi5, ...restOfJedis] = jedis;
console.log(`Jedi 4: ${jedi4}`);
console.log(`Jedi 5: ${jedi5}`);
console.log(`Jedi rest: ${restOfJedis}`);

const jediJson = {
    name : "Luke Skywalker",
    age : 35,
    sword: null,
};

const jediJsonString = JSON.stringify(jediJson);
console.log(`The best jedi: ${jediJsonString}`);
console.log(JSON.parse(jediJsonString));

const {age, name, ...restOfJediJson} = jediJson;
console.log(`Age and name of Jedi JSON: ${jediJson.age}, ${jediJson.name}`);
console.log(`Rest of Jedi JSON: ${JSON.stringify(restOfJediJson)}`);
console.log(`Full JSON: ${JSON.stringify(jediJson)}`)

if (jediJson.sword?.color === "blue") {
    console.log("Lightsaber is blue");
}

function isJediArmed(jedi) {
    return jedi.sword !== null;
}

const isJediArmedArrow = (jedi) => {
    return jedi.sword !== null;
};

console.log(isJediArmed(jediJson));
console.log(isJediArmedArrow(jediJson));

const isMyFather = async (fatherId, jedi) => {
    const response = await fetch(`https://swapi.dev/api/people/${fatherId}`);
    const responseJson = await response.json();

    if (responseJson.name === jedi) {
        console.log("Is your father");
    } else {
        console.log("Is not your father");
    }
};

isMyFather()


