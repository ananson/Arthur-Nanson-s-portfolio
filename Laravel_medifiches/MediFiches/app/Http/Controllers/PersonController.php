<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Person;

class PersonController extends Controller
{
    public function index()
    {
        $persons = Person::getAllPersons();
        return view('persons.index', compact('persons'));
    }

    public function create()
    {
        return view('persons.create');
    }

    public function store(Request $request)
    {
        $data = $request->validate([
            'national_number' => 'required',
            'first_name' => 'required',
            'last_name' => 'required',
            'email' => 'required|email',
            'role' => 'required',
        ]);

        Person::createPerson($data);

        return redirect()->route('persons.index');
    }

    public function show($id)
    {
        $person = Person::getPersonById($id);
        return view('persons.show', compact('person'));
    }

    public function edit($id)
    {
        $person = Person::getPersonById($id);
        return view('persons.edit', compact('person'));
    }

    public function update(Request $request, $id)
    {
        $data = $request->validate([
            'national_number' => 'required',
            'first_name' => 'required',
            'last_name' => 'required',
            'email' => 'required|email',
            'role' => 'required',
        ]);

        Person::updatePerson($id, $data);

        return redirect()->route('persons.index');
    }

    public function destroy($id)
    {
        Person::deletePerson($id);
        return redirect()->route('persons.index');
    }
}
