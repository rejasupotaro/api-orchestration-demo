class UsersController < ApplicationController
  def show
    id = params[:id]
    personal = {'name' => 'rejasupotaro', 'id' => id}
    render :json => personal
  end
end
