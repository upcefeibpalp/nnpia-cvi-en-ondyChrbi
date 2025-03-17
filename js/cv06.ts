let jedi1 : string = "Anakin Skywalker";
const jedi2 : string = "Obi-Wan Kenobi";

let numberOfJedis : number | string = 2;
numberOfJedis = 265;

interface Lightsaber {
    color: string
}

interface Jedi {
    name : string,
    age: number,
    sword? : Lightsaber | null
}

const jediJson = {
    name : "Luke Skywalker",
    age : 35,
    sword: null,
};

function isSkywalker(jedi : Jedi) : boolean {
    return jedi.name.includes("Skywalker");
}

console.log(isSkywalker(jediJson) === true);