@extends('layouts.app')

@section('content')
    <div class="container">
        <h2>List of My Groups</h2>
        @forelse(auth()->user()->groups as $group)
            <div class="group">
                <h3>{{ $group->name }}</h3>
                <table class="table">
                    <thead>
                    <tr>
                        <th>Member Name</th>
                        <th>Role</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    @forelse($group->members as $member)
                        <tr>
                            <td>{{ $member->name }}</td>
                            <td>{{ $member->id == $group->owner_id ? 'Owner' : 'Member' }}</td>
                            <td>
                                @if (auth()->id() == $group->owner_id && $member->id != $group->owner_id)
                                    <form method="POST" action="{{ route('deleteMember', ['group_id' => $group->group_id, 'user_id' => $member->id]) }}">
                                        @csrf
                                        @method('DELETE')
                                        <button type="submit" class="btn btn-danger">Remove</button>
                                    </form>
                                @endif
                            </td>
                        </tr>
                    @empty
                        <tr>
                            <td colspan="3">No members in this group yet.</td>
                        </tr>
                    @endforelse

                    @if (auth()->user()->belongsToGroup($group))
                        <tr>
                            <td colspan="3">
                                <a href="{{ route('createExpense', ['group_id' => $group->group_id]) }}" class="btn btn-primary">Create Expense</a>
                                <br>
                                <a href="{{ route('showGroupExpenses', ['group_id' => $group->group_id]) }}" class="btn btn-primary">View Expenses</a>
                                <br>
                                <a href="{{ route('showGroupBalances', ['group_id' => $group->group_id]) }}" class="btn btn-primary">View Balance</a>
                            </td>
                        </tr>
                    @endif
                    </tbody>
                </table>

                @if (auth()->id() == $group->owner_id)
                    <form method="POST" action="{{ route('addMember', ['group_id' => $group->group_id]) }}">
                        @csrf
                        <div class="form-group">
                            <label for="user_id">Add a member:</label>
                            <select class="form-control mt-2" id="user_id" name="user_id">
                                @foreach ($filteredUsers[$group->id] as $user)
                                    @if (!$group->members->contains('id', $user->id))
                                        <option value="{{ $user->id }}">{{ $user->name }}</option>
                                    @endif
                                @endforeach
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary mt-2">Add</button>
                    </form>
                @endif
            </div>
        @empty
            <p>No groups found for the current user.</p>
        @endforelse
    </div>



@endsection
